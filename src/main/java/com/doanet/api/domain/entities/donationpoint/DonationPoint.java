package com.doanet.api.domain.entities.donationpoint;


import com.doanet.api.domain.entities.user.User;


public class DonationPoint {
  private Long id;
  private User user;
  private String description;

  public void updateUserData(String name, String email, String phone, String description) {
    if (name != null) user.setName(name);
    if (email != null) user.setEmail(email);
    if (phone != null) user.setPhone(phone);
    if (description != null) this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    validadeUser(user);
    this.user = user;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    validadeDescription(description);
    this.description = description;
  }

  public DonationPoint(Long id, User user, String description) {
    validadeDescription(description);
    validadeUser(user);

    this.id = id;
    this.user = user;
    this.description = description;
  }

  private void validadeDescription(String description) {
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("A descrição não pode ser nula ou vazia");
    }
  }

  private void validadeUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("O usuário não pode ser nulo");
    }
  }
}
