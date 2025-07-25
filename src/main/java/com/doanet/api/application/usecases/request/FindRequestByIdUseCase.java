package com.doanet.api.application.usecases.request;

import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.domain.entities.request.Request;

public class FindRequestByIdUseCase {
  private final RequestRepository requestRepository;

  public FindRequestByIdUseCase(RequestRepository requestRepository) {
    this.requestRepository = requestRepository;
  }

  public Request execute(Long requestId) {
    return requestRepository.findById(requestId)
      .orElseThrow(() -> new IllegalArgumentException("Não foi possivel achar uma solicitação com este ID: " + requestId));
  }
}
