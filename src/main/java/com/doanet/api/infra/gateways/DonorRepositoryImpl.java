package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.domain.entities.donor.Donor;
import com.doanet.api.infra.persistence.JpaDonorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
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
  public Optional<Donor> findByIdActive(Long id) {
    return this.donorRepository.findByIdActive(id).map(mapper::toDomain);
  }

  @Override
  public Optional<Donor> findByDocumentActive(String document) {
    return this.donorRepository.findByDocumentActive(document).map(mapper::toDomain);
  }

  @Override
  public Optional<Donor> findByDocument(String document) {
    return this.donorRepository.findByDocument(document).map(mapper::toDomain);
  }

  @Override
  public Optional<Donor> findByReasonSocialIgnoreCaseActive(String reasonSocial) {
    return this.donorRepository.findByReasonSocialIgnoreCaseActive(reasonSocial).map(mapper::toDomain);
  }

  @Override
  public Optional<Donor> findByReasonSocialIgnoreCase(String reasonSocial) {
    return this.donorRepository.findByReasonSocialIgnoreCase(reasonSocial).map(mapper::toDomain);
  }

  @Override
  public void deleteById(Long id) {
    this.donorRepository.deleteById(id);
  }

  @Override
  public PageResponse<Donor> findAllActive(Pagination pagination) {
    var pageNumber = Math.max(1, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.donorRepository.findAllActive(pageable);
    var dtoPage = jpaPage.map(mapper::toDomain);

    return new PageResponse<Donor>(
      dtoPage.getContent(),
      dtoPage.getTotalElements(),
      dtoPage.getTotalPages(),
      dtoPage.getNumber() + 1
    );
  }
}
