package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.enums.RequestStatus;

import java.util.Optional;

public interface RequestRepository {
  Request save(Request request);
  Optional<Request> findById(Long id);
  PageResponse<Request> findByOngId(Long ongId, Pagination pagination);
  PageResponse<Request> findByDonationPointId(Long donationPointId, Pagination pagination);
  PageResponse<Request> findAll(Pagination pagination);
  PageResponse<Request> findByStatus(RequestStatus status, Pagination pagination);
}
