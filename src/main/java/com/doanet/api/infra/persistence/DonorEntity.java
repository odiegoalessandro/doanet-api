package com.doanet.api.infra.persistence;

import com.doanet.api.legacy.dto.CreateDonorDto;
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
public class DonorEntity {

  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private UserEntity user;


  @NotBlank
  private String document;

  @Column(name = "reason_social")
  private String reasonSocial;

  public DonorEntity(CreateDonorDto donor) {
    this.document = donor.document();
    this.reasonSocial = donor.reasonSocial();
  }

  public DonorEntity(UserEntity userEntity, String document, String reasonSocial) {
    this.user = userEntity;
    this.document = document;
    this.reasonSocial = reasonSocial;
  }
}
