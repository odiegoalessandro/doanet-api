package com.doanet.api.domain.entities.donation;

import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.domain.entities.donor.Donor;
import com.doanet.api.domain.enums.DonationStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// TODO: criando testes unitarios na classe de domínio da doação
public class Donation {
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Donor getDonor() {
    return donor;
  }

  public void setDonor(Donor donor) {
    validadeDonor(donor);
    this.donor = donor;
  }

  public DonationPoint getDonationPoint() {
    return donationPoint;
  }

  public void setDonationPoint(DonationPoint donationPoint) {
    validadeDonationPoint(donationPoint);
    this.donationPoint = donationPoint;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public List<DonationItem> getDonationItems() {
    return donationItems;
  }

  public void setDonationItems(List<DonationItem> donationItemEntities) {
    this.donationItems = donationItemEntities;
  }

  public DonationStatus getStatus() {
    return status;
  }

  public void setStatus(DonationStatus status) {
    validadeStatus(status);
    this.status = status;
  }

  public Donation(Long id,
                  Donor donor,
                  DonationPoint donationPoint,
                  LocalDate createdAt,
                  List<DonationItem> donationItems,
                  DonationStatus status) {
    validadeDonor(donor);
    validadeStatus(status);
    validadeDonationPoint(donationPoint);

    this.id = id;
    this.donor = donor;
    this.donationPoint = donationPoint;
    this.createdAt = createdAt;
    this.donationItems = donationItems;
    this.status = status;
  }

  private Long id;
  private Donor donor;
  private DonationPoint donationPoint;
  private LocalDate createdAt;
  private List<DonationItem> donationItems = new ArrayList<>();
  private DonationStatus status = DonationStatus.CREATED;

  private void validadeDonor(Donor donor){
    if(donor == null || donor.getId() == null) {
      throw new IllegalArgumentException("Donor não pode ser nulo e deve ter um ID válido");
    }
  }

  private void validadeDonationPoint(DonationPoint donationPoint){
    if(donationPoint == null || donationPoint.getId() == null) {
      throw new IllegalArgumentException("Donation Point não pode ser nulo e deve ter um ID válido");
    }
  }

  private void validadeStatus(DonationStatus status){
    if(status == null){
      throw new IllegalArgumentException("Status da doação não pode ser nulo");
    }
  }
}
