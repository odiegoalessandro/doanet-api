package com.doanet.api.application.usecases.request;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.domain.entities.request.Request;

public class FindRequestByDonationPointIdUseCase {
  private final RequestRepository requestRepository;

  public FindRequestByDonationPointIdUseCase(RequestRepository requestRepository) {
    this.requestRepository = requestRepository;
  }

  public PageResponse<Request> execute(Long donationPointId, int page, int size) {
    var pagination = new Pagination(page, size);

    return requestRepository.findByDonationPointId(donationPointId, pagination);
  }
}
