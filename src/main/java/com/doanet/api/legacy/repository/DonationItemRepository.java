package com.doanet.api.legacy.repository;

import com.doanet.api.legacy.entity.DonationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationItemRepository extends JpaRepository<DonationItem, Long> {
}
