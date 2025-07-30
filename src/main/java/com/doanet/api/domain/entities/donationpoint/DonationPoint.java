package com.doanet.api.domain.entities.donationpoint;


import com.doanet.api.domain.entities.user.User;

// TODO: criar validação desses campos no construtor
// TODO: criar testes unitarios na classe de dominio do ponto de doação
public class DonationPoint {
  private Long id;
  private User user;
  private String description;

  public void updateUserData(String name, String email, String phone, String description) {
    // TODO: criar validação desses campos antes de settar eles
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
    this.user = user;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DonationPoint(Long id, User user, String description) {
    this.id = id;
    this.user = user;
    this.description = description;
  }
}
