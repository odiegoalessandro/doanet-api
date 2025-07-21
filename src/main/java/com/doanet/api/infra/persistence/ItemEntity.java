package com.doanet.api.infra.persistence;

import com.doanet.api.legacy.dto.CreateItemDto;
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
public class ItemEntity {
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

  public ItemEntity(String name, String description, boolean isPerishable, LocalDate expirationDate){
    this.name = name;
    this.description = description;
    this.isPerishable = isPerishable;
    this.expirationDate = expirationDate;
  }
}
