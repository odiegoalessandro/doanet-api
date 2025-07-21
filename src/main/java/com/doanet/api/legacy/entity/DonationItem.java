package com.doanet.api.legacy.entity;

import com.doanet.api.infra.persistence.ItemEntity;
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
  private ItemEntity item;

  @ManyToOne
  @JoinColumn(name = "donation_id")
  private Donation donation;

  public DonationItem(ItemEntity item, Donation donation, Integer quantity) {
    this.item = item;
    this.donation = donation;
    this.quantity = quantity;
  }
}
