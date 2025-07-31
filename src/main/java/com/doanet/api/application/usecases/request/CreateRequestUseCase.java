package com.doanet.api.application.usecases.request;

import com.doanet.api.application.commands.CreateRequestCommand;
import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.application.usecases.donationpoint.FindDonationPointByIdUseCase;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.application.usecases.ong.FindActiveOngByIdUseCase;
 import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.entities.request.RequestItem;
import com.doanet.api.domain.enums.RequestStatus;

import java.time.LocalDate;
public class CreateRequestUseCase {
  private final RequestRepository requestRepository;
  private final FindActiveOngByIdUseCase findActiveOngByIdUseCase;
  private final FindDonationPointByIdUseCase findDonationPointByIdUseCase;
  private final FindItemByIdUseCase findItemByIdUseCase;


  public CreateRequestUseCase(RequestRepository requestRepository,
                              FindActiveOngByIdUseCase findActiveOngByIdUseCase,
                              FindDonationPointByIdUseCase findDonationPointByIdUseCase,
                              FindItemByIdUseCase findItemByIdUseCase) {
    this.requestRepository = requestRepository;
    this.findActiveOngByIdUseCase = findActiveOngByIdUseCase;
    this.findDonationPointByIdUseCase = findDonationPointByIdUseCase;
    this.findItemByIdUseCase = findItemByIdUseCase;
  }

  public Request execute(CreateRequestCommand requestCommand) {
    var donationPoint = findDonationPointByIdUseCase.execute(requestCommand.donationPointId(), true);
    var ong = findActiveOngByIdUseCase.execute(requestCommand.ongId());

    if(!ong.getUser().isActive() || !donationPoint.getUser().isActive()) {
      throw new IllegalStateException("Não foi possivel realizar a solicitação. Ong ou ponto de doação não estão " +
        "ativos.");
    }

    var request = new Request(null, donationPoint, ong, LocalDate.now(), null, RequestStatus.CREATED);
    var items = requestCommand.items().stream().map(item -> {
      var itemEntity = findItemByIdUseCase.execute(item.itemId());
      return new RequestItem(null, request, itemEntity, item.quantity());
    }).toList();

    request.setItems(items);

    return requestRepository.save(request);
  }
}
