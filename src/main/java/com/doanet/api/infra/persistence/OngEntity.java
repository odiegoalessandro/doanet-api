package com.doanet.api.infra.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "ong")
@Entity
public class OngEntity {
  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @NotNull
  @Pattern(regexp = "\\d{14}", message = "CNPJ inválido")
  private String cnpj;

  public OngEntity(UserEntity user, String cnpj){
    this.user = user;
    this.cnpj = cnpj;
  }
}
