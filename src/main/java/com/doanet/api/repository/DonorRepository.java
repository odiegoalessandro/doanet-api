package com.doanet.api.repository;

import com.doanet.api.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
  Optional<Donor> findByDocument(String document);
  Optional<Donor> findByReasonSocialIgnoreCase(String reasonSocial);
}
