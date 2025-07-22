package com.doanet.api.infra.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "donation_point")
public class DonationPointEntity {
  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private UserEntity user;


  @NotBlank
  private String description;
}
