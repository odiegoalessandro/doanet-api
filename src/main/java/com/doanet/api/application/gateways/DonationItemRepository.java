package com.doanet.api.application.gateways;

import com.doanet.api.domain.entities.donation.Donation;

import java.util.Optional;

public interface DonationItemRepository {
  Donation save(Donation donation);
  Optional<Donation> findById(Long id);
  void deleteById(Long id);
}
