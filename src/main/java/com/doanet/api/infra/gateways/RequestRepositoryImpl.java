package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.enums.RequestStatus;
import com.doanet.api.infra.persistence.JpaItemRepository;
import com.doanet.api.infra.persistence.JpaRequestRepository;
import com.doanet.api.infra.persistence.RequestItemEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class RequestRepositoryImpl implements RequestRepository {
  private final JpaRequestRepository jpaRequestRepository;
  private final  RequestEntityMapper requestMapper;
  private final JpaItemRepository jpaItemRepository;

  public RequestRepositoryImpl(JpaRequestRepository jpaRequestRepository, RequestEntityMapper requestMapper,
                               JpaItemRepository jpaItemRepository) {
    this.jpaRequestRepository = jpaRequestRepository;
    this.requestMapper = requestMapper;
    this.jpaItemRepository = jpaItemRepository;
  }

  @Override
  public Request save(Request request) {
    var entity = this.requestMapper.toEntity(request);

    for (RequestItemEntity itemEntity : entity.getItems()) {
      var managedItem = jpaItemRepository.findById(itemEntity.getItem().getId())
        .orElseThrow(() -> new EntityNotFoundException("Item not found: " + itemEntity.getItem().getId()));

      itemEntity.setItem(managedItem);
    }

    var saved = jpaRequestRepository.save(entity);

    return this.requestMapper.toDomain(saved);
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
