package com.doanet.api.infra.persistence;

import jakarta.persistence.*;
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

  private String document;

  @Column(name = "reason_social")
  private String reasonSocial;


  public DonorEntity(UserEntity userEntity, String document, String reasonSocial) {
    this.user = userEntity;
    this.document = document;
    this.reasonSocial = reasonSocial;
  }
}
