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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
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
