package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.enums.DonationStatus;
import com.doanet.api.infra.persistence.DonationItemEntity;
import com.doanet.api.infra.persistence.JpaDonationRepository;
import com.doanet.api.infra.persistence.JpaItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public class DonationRepositoryImpl implements DonationRepository {
  private final JpaDonationRepository jpaDonationRepository;
  private final DonationEntityMapper donationMapper;
  private final JpaItemRepository jpaItemRepository;

  public DonationRepositoryImpl(DonationEntityMapper donationMapper, JpaDonationRepository jpaDonationRepository,
                                JpaItemRepository jpaItemRepository) {
    this.donationMapper = donationMapper;
    this.jpaDonationRepository = jpaDonationRepository;
    this.jpaItemRepository = jpaItemRepository;
  }

  @Override
  public Donation save(Donation donation) {
    var entity = this.donationMapper.toEntity(donation);

    for (DonationItemEntity itemEntity : entity.getDonationItems()) {
      var managedItem = this.jpaItemRepository.findById(itemEntity.getItem().getId())
        .orElseThrow(() -> new EntityNotFoundException("Item not found: " + itemEntity.getItem().getId()));

      itemEntity.setItem(managedItem);
    }

    var saved = jpaDonationRepository.save(entity);
    return this.donationMapper.toDomain(saved);
  }

  @Override
  public Optional<Donation> findById(Long id) {
    return this.jpaDonationRepository.findById(id).map(donationMapper::toDomain);
  }

  @Override
  public PageResponse<Donation> findByDonorId(Long donorId, Pagination pagination) {
    var pageNumber = Math.max(0, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaDonationRepository.findByDonorId(donorId, pageable);
    var donations = jpaPage.getContent()
      .stream()
      .map(this.donationMapper::toDomain)
      .toList();

    return new PageResponse<>(
      donations,
      jpaPage.getTotalElements(),
      jpaPage.getTotalPages(),
      jpaPage.getNumber() + 1
    );
  }

  @Override
  public PageResponse<Donation> findByDonationPointId(Long donationPointId, Pagination pagination) {
    var pageNumber = Math.max(0, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaDonationRepository.findByDonationPointId(donationPointId, pageable);
    var donations = jpaPage.getContent()
      .stream()
      .map(this.donationMapper::toDomain)
      .toList();

    return new PageResponse<>(
      donations,
      jpaPage.getTotalElements(),
      jpaPage.getTotalPages(),
      jpaPage.getNumber() + 1
    );
  }

  @Override
  public PageResponse<Donation> findAll(Pagination pagination) {
    var pageNumber = Math.max(0, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaDonationRepository.findAll(pageable);
    var donations = jpaPage.getContent()
      .stream()
      .map(this.donationMapper::toDomain)
      .toList();

    return new PageResponse<>(
      donations,
      jpaPage.getTotalElements(),
      jpaPage.getTotalPages(),
      jpaPage.getNumber() + 1
    );
  }

  @Override
  public PageResponse<Donation> findByStatus(DonationStatus status, Pagination pagination) {
    var pageNumber = Math.max(0, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaDonationRepository.findByStatus(status, pageable);
    var donations = jpaPage.getContent()
      .stream()
      .map(this.donationMapper::toDomain)
      .toList();

    return new PageResponse<>(
      donations,
      jpaPage.getTotalElements(),
      jpaPage.getTotalPages(),
      jpaPage.getNumber() + 1
    );
  }
}
