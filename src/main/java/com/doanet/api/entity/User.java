package com.doanet.api.entity;

import com.doanet.api.dto.CreateUserDto;
import com.doanet.api.enums.UserType;
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
public class User {

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
  public User(CreateUserDto user) {
    this.city = user.city();
    this.email = user.email();
    this.neighborhood = user.neighborhood();
    this.number = user.number();
    this.password = user.password();
    this.phone = user.phone();
    this.state = user.state();
    this.street = user.street();
    this.zipCode = user.zipCode();
    this.name = user.name();
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
