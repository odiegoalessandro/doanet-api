package com.doanet.api.application.usecases.request;

import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.enums.RequestStatus;
import com.doanet.api.domain.validator.RequestStatusValidator;

public class UpdateRequestStatusUseCase {
  private final RequestRepository requestRepository;
  private final FindRequestByIdUseCase findRequestByIdUseCase;

  public UpdateRequestStatusUseCase(RequestRepository requestRepository, FindRequestByIdUseCase findRequestByIdUseCase) {
    this.requestRepository = requestRepository;
    this.findRequestByIdUseCase = findRequestByIdUseCase;
  }

  public Request execute(Long requestId, RequestStatus status) {
    var request = this.findRequestByIdUseCase.execute(requestId);

    RequestStatusValidator.validate(request.getStatus());

    request.setStatus(status);

    return requestRepository.save(request);
  }
}
