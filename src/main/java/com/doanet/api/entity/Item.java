package com.doanet.api.entity;

import com.doanet.api.dto.CreateItemDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "item")
@Table(name = "item")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  private String description;

  @Column(name = "is_perishable", columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean isPerishable;

  @Column(name = "expiration_date", nullable = true, columnDefinition = "DATE DEFAULT NULL")
  private LocalDate expirationDate;

  public Item(CreateItemDto itemDto) {
    this.name = itemDto.name();
    this.description = itemDto.description();
    this.isPerishable = itemDto.isPerishable();
    this.expirationDate = itemDto.expirationDate();
  }
}
