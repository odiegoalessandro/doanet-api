package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donor.Donor;
import com.doanet.api.infra.persistence.JpaDonorRepository;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class DonorRepositoryImpl implements DonorRepository {
  private final JpaDonorRepository donorRepository;
  private final DonorEntityMappper mapper;

  public DonorRepositoryImpl(JpaDonorRepository donorRepository, DonorEntityMappper mapper){
    this.donorRepository = donorRepository;
    this.mapper = mapper;
  }

  @Override
  public Donor save(Donor donor) {
    var donorEntity = this.donorRepository.save(this.mapper.toEntity(donor));

    return this.mapper.toDomain(donorEntity);
  }

  @Override
  public Optional<Donor> findById(Long id, boolean isActive) {
    return this.donorRepository.findById(id, isActive).map(mapper::toDomain);
  }

  @Override
  public Optional<Donor> findByDocument(String document, boolean isActive) {
    return this.donorRepository.findByDocument(document, isActive).map(mapper::toDomain);
  }

  @Override
  public void deleteById(Long id) {
    this.donorRepository.deleteById(id);
  }

  @Override
  public PageResponse<Donor> findByReasonSocialIgnoreCase(String reasonSocial, Pagination pagination, boolean isActive) {
    var pageNumber = Math.max(1, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.donorRepository.findByReasonSocialIgnoreCase(reasonSocial, pageable, isActive);
    var dtoPage = jpaPage.map(mapper::toDomain);

    return new PageResponse<Donor>(
      dtoPage.getContent(),
      dtoPage.getTotalElements(),
      dtoPage.getTotalPages(),
      dtoPage.getNumber() + 1
    );
  }

  @Override
  public PageResponse<Donor> findAll(Pagination pagination, boolean isActive) {
    var pageNumber = Math.max(1, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.donorRepository.findAll(pageable, isActive);
    var dtoPage = jpaPage.map(mapper::toDomain);

    return new PageResponse<Donor>(
      dtoPage.getContent(),
      dtoPage.getTotalElements(),
      dtoPage.getTotalPages(),
      dtoPage.getNumber() + 1
    );
  }
}
