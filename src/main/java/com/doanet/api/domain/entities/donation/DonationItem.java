package com.doanet.api.domain.entities.donation;

import com.doanet.api.domain.entities.item.Item;

public class DonationItem {
  private Long id;
  private Integer quantity;
  private Item item;
  private Donation donation;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Donation getDonation() {
    return donation;
  }

  public void setDonation(Donation donation) {
    this.donation = donation;
  }

  public DonationItem(Long id, Integer quantity, Item item, Donation donation) {
    this.id = id;
    this.quantity = quantity;
    this.item = item;
    this.donation = donation;
  }
}
