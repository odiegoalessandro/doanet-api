package com.doanet.api.legacy.entity;

import com.doanet.api.infra.persistence.UserEntity;
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
public class Ong {
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
}
