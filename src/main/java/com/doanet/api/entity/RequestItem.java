package com.doanet.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "request_item")
public class RequestItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "request_id")
  private Request request;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  @Column(columnDefinition = "INT DEFAULT 1")
  private Integer quantity;

  public RequestItem(Request request, Item item, @NotNull Integer quantity) {
    this.request = request;
    this.item = item;
    this.quantity = quantity;
  }
}
