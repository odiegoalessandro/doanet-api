package com.doanet.api.application.usecases.request;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.enums.RequestStatus;

public class FindRequestByStatusUseCase {
  private final RequestRepository requestRepository;

  public FindRequestByStatusUseCase(RequestRepository requestRepository) {
    this.requestRepository = requestRepository;
  }

  public PageResponse<Request> execute(RequestStatus status, int page, int size) {
    var pagination = new Pagination(page, size);

    return requestRepository.findByStatus(status, pagination);
  }
}
