package com.doanet.api.domain.entities.user;

import com.doanet.api.domain.enums.UserType;

import java.util.Locale;

public class User {
  private Long id;
  private String name;
  private String email;
  private String password;
  private String phone;
  private String street;
  private String number;
  private String neighborhood;
  private String city;
  private String state;
  private String zipCode;
  private Double latitude;
  private Double longitude;
  private UserType userType;
  private boolean isActive = true;

  public User(Long id,
              String name,
              String email,
              String password,
              String phone,
              String street,
              String number,
              String neighborhood,
              String city,
              String state,
              String zipCode,
              Double latitude,
              Double longitude,
              UserType userType,
              boolean isActive) {
    validateName(name);
    validatePhone(phone);
    validateZipCode(zipCode);
    validateUserType(userType);

    validadeIsNotBlank(street, "Rua");
    validadeIsNotBlank(number, "Número");
    validadeIsNotBlank(neighborhood, "Bairro");
    validadeIsNotBlank(city, "Cidade");
    validadeIsNotBlank(state, "Estado");

    this.id = id;
    this.name = name;
    this.email = normalizeEmail(email);
    this.password = password;
    this.phone = phone;
    this.street = street;
    this.number = number;
    this.neighborhood = neighborhood;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.latitude = latitude;
    this.longitude = longitude;
    this.userType = userType;
    this.isActive = isActive;
  }

  public void setName(String name) {
    validateName(name);
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = normalizeEmail(email);
  }

  public void setPhone(String phone) {
    validatePhone(phone);
    this.phone = phone;
  }

  public void setZipCode(String zipCode) {
    validateZipCode(zipCode);
    this.zipCode = zipCode;
  }

  public void setUserType(UserType userType) {
    validateUserType(userType);
    this.userType = userType;
  }

  public void setId(Long id) { this.id = id; }

  public void setPassword(String password) {
    validatePassword(password);
    this.password = password;
  }

  public void setStreet(String street) {
    validadeIsNotBlank(street, "Rua");
    this.street = street;
  }

  public void setNumber(String number) {
    validadeIsNotBlank(number, "Número");
    this.number = number;
  }

  public void setNeighborhood(String neighborhood) {
    validadeIsNotBlank(neighborhood, "Bairro");
    this.neighborhood = neighborhood;
  }

  public void setCity(String city) {
    validadeIsNotBlank(city, "Cidade");
    this.city = city;
  }

  public void setState(String state) {
    validadeIsNotBlank(state, "Estado");
    this.state = state;
  }

  public void setLatitude(Double latitude) { this.latitude = latitude; }

  public void setLongitude(Double longitude) { this.longitude = longitude; }

  public void setActive(boolean active) { isActive = active; }

  public Long getId() { return id; }
  public String getName() { return name; }
  public String getEmail() { return email; }
  public String getPassword() { return password; }
  public String getPhone() { return phone; }
  public String getStreet() { return street; }
  public String getNumber() { return number; }
  public String getNeighborhood() { return neighborhood; }
  public String getCity() { return city; }
  public String getState() { return state; }
  public String getZipCode() { return zipCode; }
  public Double getLatitude() { return latitude; }
  public Double getLongitude() { return longitude; }
  public UserType getUserType() { return userType; }
  public boolean isActive() { return isActive; }

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

  private void validatePassword(String password) {
    if (password == null || password.trim().length() < 6)
      throw new IllegalArgumentException("Senha deve conter no mínimo 6 caracteres");
  }

  private void validadeIsNotBlank(String content, String fieldName) {
    if (content == null || content.trim().isEmpty())
      throw new IllegalArgumentException(fieldName + " é obrigatório(a)");
  }

  private void validateName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }
  }

  private String normalizeEmail(String email) {
    if (email == null || !email.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
      throw new IllegalArgumentException("Email deve ter padrão válido");
    }
    return email.trim().toLowerCase(Locale.ROOT);
  }

  private void validatePhone(String phone) {
    if (phone == null || phone.length() < 8) {
      throw new IllegalArgumentException("Telefone inválido");
    }
  }

  private void validateZipCode(String zipCode) {
    if (zipCode == null || !zipCode.matches("\\d{5}-?\\d{3}")) {
      throw new IllegalArgumentException("CEP inválido");
    }
  }

  private void validateUserType(UserType userType) {
    if (userType == null) {
      throw new IllegalArgumentException("O tipo do usuário não deve ser nulo");
    }
  }
}
