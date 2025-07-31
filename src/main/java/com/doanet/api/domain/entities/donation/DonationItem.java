package com.doanet.api.domain.entities.donation;

import com.doanet.api.domain.entities.item.Item;

// TODO: criar testes unitarios na classe de dominio do donationitem
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
    validateQuantity(quantity);
    this.quantity = quantity;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    validateItem(item);
    this.item = item;
  }

  public Donation getDonation() {
    return donation;
  }

  public void setDonation(Donation donation) {
    this.donation = donation;
  }

  public DonationItem(Long id, Integer quantity, Item item, Donation donation) {
    validateItem(item);
    validateQuantity(quantity);

    this.id = id;
    this.quantity = quantity;
    this.item = item;
    this.donation = donation;
  }

  private void validateQuantity(Integer quantity) {
    if (quantity == null || quantity <= 0)
      throw new IllegalArgumentException("Quantidade deve ser maior que zero");
  }

  private void validateItem(Item item) {
    if (item == null || item.getId() == null)
      throw new IllegalArgumentException("Item inválido");
  }
}
