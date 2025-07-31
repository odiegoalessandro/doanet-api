package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.infra.persistence.JpaDonationPointRepository;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class DonationPointRepositoryImpl implements DonationPointRepository {
  private final JpaDonationPointRepository donationPointRepository;
  private final DonationPointEntityMapper mapper;

  public DonationPointRepositoryImpl(JpaDonationPointRepository donationPointRepository,
                                     DonationPointEntityMapper mapper) {
    this.donationPointRepository = donationPointRepository;
    this.mapper = mapper;
  }

  @Override
  public DonationPoint save(DonationPoint donationPoint) {
    var entity = this.mapper.toEntity(donationPoint);
    var saved = this.donationPointRepository.save(entity);

    return this.mapper.toDomain(saved);
  }

  @Override
  public Optional<DonationPoint> findById(Long id, boolean isActive) {
    return this.donationPointRepository.findById(id, isActive).map(mapper::toDomain);
  }

  @Override
  public void deleteBy(Long id) {
    this.donationPointRepository.deleteById(id);
  }

  @Override
  public PageResponse<DonationPoint> findByDescriptionIgnoreCase(
    String description,
    boolean isActive,
    Pagination pagination
  ) {
    var pageNumber = Math.max(1, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.donationPointRepository.findByDescriptionIgnoreCase(
      description,
      isActive,
      pageable
    );
    var dtoPage = jpaPage.map(mapper::toDomain);


    return new PageResponse<DonationPoint>(
      dtoPage.getContent(),
      dtoPage.getTotalElements(),
      dtoPage.getTotalPages(),
      dtoPage.getNumber()
    );
  }

  @Override
  public PageResponse<DonationPoint> findAll(boolean isActive, Pagination pagination) {
    var pageNumber = Math.max(1, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.donationPointRepository.findAll(isActive, pageable);
    var dtoPage = jpaPage.map(mapper::toDomain);

    return new PageResponse<DonationPoint>(
      dtoPage.getContent(),
      dtoPage.getTotalElements(),
      dtoPage.getTotalPages(),
      dtoPage.getNumber() + 1
    );
  }

  @Override
  public void deleteById(Long id) {
    this.donationPointRepository.deleteById(id);
  }
}
