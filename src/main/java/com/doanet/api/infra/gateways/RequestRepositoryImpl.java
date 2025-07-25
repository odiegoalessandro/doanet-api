package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.enums.RequestStatus;
import com.doanet.api.infra.persistence.JpaRequestRepository;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class RequestRepositoryImpl implements RequestRepository {
  private final JpaRequestRepository jpaRequestRepository;
  private final  RequestEntityMapper requestMapper;

  public RequestRepositoryImpl(JpaRequestRepository jpaRequestRepository, RequestEntityMapper requestMapper) {
    this.jpaRequestRepository = jpaRequestRepository;
    this.requestMapper = requestMapper;
  }

  @Override
  public Request save(Request request) {
    var requestEntity = this.requestMapper.toEntity(request);
    var savedEntity = this.jpaRequestRepository.save(requestEntity);

    return this.requestMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Request> findById(Long id) {
    return this.jpaRequestRepository.findById(id).map(this.requestMapper::toDomain);
  }

  @Override
  public PageResponse<Request> findByOngId(Long ongId, Pagination pagination) {
    var pageNumber = Math.max(0, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaRequestRepository.findByOngId(ongId, pageable);
    var requests = jpaPage.getContent()
      .stream()
      .map(this.requestMapper::toDomain)
      .toList();

    return new PageResponse<>(
      requests,
      jpaPage.getTotalElements(),
      jpaPage.getTotalPages(),
      jpaPage.getNumber() + 1
    );
  }

  @Override
  public PageResponse<Request> findByDonationPointId(Long donationPointId, Pagination pagination) {
    var pageNumber = Math.max(0, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaRequestRepository.findByDonationPointId(donationPointId, pageable);
    var requests = jpaPage.getContent()
      .stream()
      .map(this.requestMapper::toDomain)
      .toList();

    return new PageResponse<>(
      requests,
      jpaPage.getTotalElements(),
      jpaPage.getTotalPages(),
      jpaPage.getNumber() + 1
    );
  }

  @Override
  public PageResponse<Request> findAll(Pagination pagination) {
    var pageNumber = Math.max(0, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaRequestRepository.findAll(pageable);
    var requests = jpaPage.getContent()
      .stream()
      .map(this.requestMapper::toDomain)
      .toList();

    return new PageResponse<>(
      requests,
      jpaPage.getTotalElements(),
      jpaPage.getTotalPages(),
      jpaPage.getNumber() + 1
    );
  }

  @Override
  public PageResponse<Request> findByStatus(RequestStatus status, Pagination pagination) {
    var pageNumber = Math.max(0, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaRequestRepository.findByStatus(status, pageable);
    var requests = jpaPage.getContent()
      .stream()
      .map(this.requestMapper::toDomain)
      .toList();

    return new PageResponse<>(
      requests,
      jpaPage.getTotalElements(),
      jpaPage.getTotalPages(),
      jpaPage.getNumber() + 1
    );
  }
}
