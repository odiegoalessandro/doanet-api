package com.doanet.api.infra.persistence;

import jakarta.persistence.*;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private UserEntity user;

  @Pattern(regexp = "\\d{14}", message = "CNPJ inválido")
  private String cnpj;

  public OngEntity(UserEntity user, String cnpj){
    this.user = user;
    this.cnpj = cnpj;
  }
}
