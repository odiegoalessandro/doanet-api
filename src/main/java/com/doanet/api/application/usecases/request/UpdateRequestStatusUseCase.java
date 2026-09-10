package com.doanet.api.application.usecases.request;

import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import com.doanet.api.domain.enums.RequestStatus;
import com.doanet.api.domain.validator.RequestStatusValidator;

import java.util.Map;

public class UpdateRequestStatusUseCase {
  private final RequestRepository requestRepository;
  private final FindRequestByIdUseCase findRequestByIdUseCase;
  private final RecordAuditUseCase recordAuditUseCase;

  public UpdateRequestStatusUseCase(
    RequestRepository requestRepository,
    FindRequestByIdUseCase findRequestByIdUseCase,
    RecordAuditUseCase recordAuditUseCase
  ) {
    this.requestRepository = requestRepository;
    this.findRequestByIdUseCase = findRequestByIdUseCase;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public Request execute(Long requestId, RequestStatus status) {
    var request = this.findRequestByIdUseCase.execute(requestId);

    RequestStatusValidator.validate(request.getStatus());

    var previousStatus = request.getStatus();
    request.setStatus(status);

    var savedRequest = requestRepository.save(request);

    this.recordAuditUseCase.execute(
      AuditAction.STATUS_CHANGE,
      AuditedEntity.REQUEST,
      savedRequest.getId(),
      Map.of("status", previousStatus.name()),
      Map.of("status", savedRequest.getStatus().name())
    );

    return savedRequest;
  }
}
