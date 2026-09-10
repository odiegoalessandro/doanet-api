package com.doanet.api.application.usecases.request;

import com.doanet.api.application.commands.CreateRequestCommand;
import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.donationpoint.FindDonationPointByIdUseCase;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.application.usecases.ong.FindOngByIdUseCase;
import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.entities.request.RequestItem;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import com.doanet.api.domain.enums.RequestStatus;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateRequestUseCase {
  private final RequestRepository requestRepository;
  private final FindOngByIdUseCase findOngByIdUseCase;
  private final FindDonationPointByIdUseCase findDonationPointByIdUseCase;
  private final FindItemByIdUseCase findItemByIdUseCase;
  private final RecordAuditUseCase recordAuditUseCase;

  public CreateRequestUseCase(
      RequestRepository requestRepository,
      FindOngByIdUseCase findOngByIdUseCase,
      FindDonationPointByIdUseCase findDonationPointByIdUseCase,
      FindItemByIdUseCase findItemByIdUseCase,
      RecordAuditUseCase recordAuditUseCase) {
    this.requestRepository = requestRepository;
    this.findOngByIdUseCase = findOngByIdUseCase;
    this.findDonationPointByIdUseCase = findDonationPointByIdUseCase;
    this.findItemByIdUseCase = findItemByIdUseCase;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public Request execute(CreateRequestCommand requestCommand) {
    var donationPoint =
        findDonationPointByIdUseCase.execute(requestCommand.donationPointId(), true);
    var ong = findOngByIdUseCase.execute(requestCommand.ongId(), true);

    if (!ong.getUser().isActive() || !donationPoint.getUser().isActive()) {
      throw new IllegalStateException(
          "Não foi possivel realizar a solicitação. Ong ou ponto de doação não estão " + "ativos.");
    }

    var request =
        new Request(null, donationPoint, ong, LocalDate.now(), null, RequestStatus.CREATED);
    var items =
        requestCommand.items().stream()
            .map(
                item -> {
                  var itemEntity = findItemByIdUseCase.execute(item.itemId());
                  return new RequestItem(null, request, itemEntity, item.quantity());
                })
            .toList();

    request.setItems(items);

    var savedRequest = requestRepository.save(request);

    this.recordAuditUseCase.execute(
        AuditAction.CREATE,
        AuditedEntity.REQUEST,
        savedRequest.getId(),
        null,
        this.auditStateOf(savedRequest));

    return savedRequest;
  }

  private Map<String, Object> auditStateOf(Request request) {
    var state = new LinkedHashMap<String, Object>();
    state.put("donationPointId", request.getDonationPoint().getId());
    state.put("ongId", request.getOng().getId());
    state.put("createdAt", request.getCreatedAt());
    state.put("status", request.getStatus());
    state.put(
        "items",
        request.getItems().stream()
            .map(
                requestItem ->
                    Map.of(
                        "itemId", requestItem.getItem().getId(),
                        "quantity", requestItem.getQuantity()))
            .toList());

    return state;
  }
}
