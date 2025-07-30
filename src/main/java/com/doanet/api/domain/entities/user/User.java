package com.doanet.api.domain.entities.user;

import com.doanet.api.domain.enums.UserType;

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

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getPhone() {
    return phone;
  }

  public String getStreet() {
    return street;
  }

  public String getNumber() {
    return number;
  }

  public String getNeighborhood() {
    return neighborhood;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public UserType getUserType() {
    return userType;
  }

  public boolean isActive() {
    return isActive;
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

    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }

    if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
      throw new IllegalArgumentException("Email deve ter padrão válido");
    }

    if (phone == null || phone.length() < 8){
      throw new IllegalArgumentException("Telefone inválido");
    }

    if(userType == null){
      throw new IllegalArgumentException("O tipo do usuario não deve ser nulo");
    }

    if (zipCode == null || !zipCode.matches("\\d{5}-?\\d{3}")) {
      throw new IllegalArgumentException("CEP inválido");
    }

    this.id = id;
    this.name = name;
    this.email = email;
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

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public void setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
