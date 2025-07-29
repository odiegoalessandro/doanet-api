package com.doanet.api.infra.persistence;

import com.doanet.api.domain.entities.donation.DonationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDonationItemRepository extends JpaRepository<DonationItemEntity, Long> {
}
