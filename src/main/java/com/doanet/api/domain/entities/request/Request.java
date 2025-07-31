package com.doanet.api.domain.entities.request;

import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.domain.enums.RequestStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// TODO: Criar testes unitários para a classe Request
public class Request {
  private Long id;
  private DonationPoint donationPoint;
  private Ong ong;
  private LocalDate createdAt;
  private List<RequestItem> items = new ArrayList<>();
  private RequestStatus status = RequestStatus.CREATED;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public DonationPoint getDonationPoint() {
    return donationPoint;
  }

  public void setDonationPoint(DonationPoint donationPoint) {
    validadeDonationPoint(donationPoint);
    this.donationPoint = donationPoint;
  }

  public Ong getOng() {
    return ong;
  }

  public void setOng(Ong ong) {
    validadeOng(ong);
    this.ong = ong;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public List<RequestItem> getItems() {
    return items;
  }

  public void setItems(List<RequestItem> items) {
    this.items = items;
  }

  public RequestStatus getStatus() {
    return status;
  }

  public void setStatus(RequestStatus status) {
    validadeStatus(status);
    this.status = status;
  }

  public Request(Long id,
                 DonationPoint donationPoint,
                 Ong ong,
                 LocalDate createdAt,
                 List<RequestItem> items,
                 RequestStatus status) {
    validadeOng(ong);
    validadeDonationPoint(donationPoint);
    validadeStatus(status);

    this.id = id;
    this.donationPoint = donationPoint;
    this.ong = ong;
    this.createdAt = createdAt;
    this.items = items;
    this.status = status;
  }

  private void validadeDonationPoint(DonationPoint donationPoint) {
    if (donationPoint == null) {
      throw new IllegalArgumentException("Donation point não deve ser nula");
    }
  }

  private void validadeOng(Ong ong) {
    if (ong == null) {
      throw new IllegalArgumentException("ONG não deve ser nula");
    }
  }

  private void validadeStatus(RequestStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("Status não deve ser nulo");
    }
  }

}
