package com.doanet.api.infra.persistence;

import com.doanet.api.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Setter
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank(message = "Email is required")
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @NotBlank(message = "Password is required")
  @Column(name = "password_hash", nullable = false)
  @JsonIgnore
  @Schema(hidden = true)
  private String password;

  @NotBlank
  private String phone;

  @NotBlank
  private String street;

  @NotBlank
  private String number;

  @NotBlank
  private String neighborhood;

  @NotBlank
  private String city;

  @NotBlank
  private String state;

  @NotBlank
  private String zipCode;

  @NotNull
  private Double latitude;

  @NotNull
  private Double longitude;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_type")
  private UserType userType;

  @NotNull
  @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE NOT NULL")
  private boolean isActive = true;

  public UserEntity(String city,
                    String email,
                    String neighborhood,
                    String number,
                    String password,
                    String phone,
                    String state,
                    String street,
                    String zipCode,
                    String name,
                    boolean isActive) {
    this.city = city;
    this.email = email;
    this.neighborhood = neighborhood;
    this.number = number;
    this.password = password;
    this.phone = phone;
    this.state = state;
    this.street = street;
    this.zipCode = zipCode;
    this.name = name;
    this.isActive = isActive;
  }

  public String buildAddress() {
    return String.join(", ",
      street,
      number,
      neighborhood,
      city,
      state,
      "Brazil",
      zipCode
    );
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }
}
