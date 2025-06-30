package com.doanet.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "donation_item")
public class DonationItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "INT DEFAULT 1")
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item donationItem;

  @ManyToOne
  @JoinColumn(name = "donation_id")
  private Donation donation;

  public DonationItem(Item item, Donation donation, Integer quantity) {
    this.donationItem = item;
    this.donation = donation;
    this.quantity = quantity;
  }
}
