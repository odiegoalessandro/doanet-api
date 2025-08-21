package com.doanet.api.infra.persistence;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "request_item")
public class RequestItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "request_id")
  private RequestEntity request;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private ItemEntity item;

  @Column(columnDefinition = "INT DEFAULT 1")
  private Integer quantity;

  public RequestItemEntity(Long id, RequestEntity request, ItemEntity item, Integer quantity) {
    this.id = id;
    this.request = request;
    this.item = item;
    this.quantity = quantity;
  }
}
