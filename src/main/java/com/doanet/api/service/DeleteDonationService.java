package com.doanet.api.service;

import com.doanet.api.entity.Donation;
import com.doanet.api.enums.Status;
import com.doanet.api.repository.DonationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DeleteDonationService {
  private static final Set<Status> NOT_ALLOWED_TO_DELETE = Set.of(
    Status.BLOCKED,
    Status.DELIVERED,
    Status.EXPIRED,
    Status.CANCELLED,
    Status.IN_TRANSIT
  );

  private DonationRepository donationRepository;

  public DeleteDonationService(DonationRepository donationRepository) {
    this.donationRepository = donationRepository;
  }

  public Donation delete(Long donationId){
    var donation = this.donationRepository.findById(donationId)
      .orElseThrow(() -> new EntityNotFoundException("Não foi possivel achar doações com este ID"));

    if(NOT_ALLOWED_TO_DELETE.contains(donation.getStatus())){
      throw new IllegalStateException("Não é possivel deletar uma doação com movimentações em aberto");
    }

    donation.setStatus(Status.CANCELLED);

    return this.donationRepository.save(donation);
  }
}
