package com.doanet.api.entity;

import com.doanet.api.dto.CreateDonorDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "donor")
public class Donor {

  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;


  @NotBlank
  private String document;

  @Column(name = "reason_social")
  private String reasonSocial;

  public Donor(CreateDonorDto donor) {
    this.document = donor.document();
    this.reasonSocial = donor.reasonSocial();
  }
}
