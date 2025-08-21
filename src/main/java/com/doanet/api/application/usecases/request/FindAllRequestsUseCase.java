package com.doanet.api.application.usecases.request;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.domain.entities.request.Request;

public class FindAllRequestsUseCase {
  private final RequestRepository requestRepository;

  public FindAllRequestsUseCase(RequestRepository requestRepository) {
    this.requestRepository = requestRepository;
  }

  public PageResponse<Request> execute(int page, int size) {
    var pagination = new Pagination(page, size);

    return requestRepository.findAll(pagination);
  }
}
