package com.doanet.api.domain.entities.donationpoint;


import com.doanet.api.domain.entities.user.User;

public class DonationPoint {
  private Long id;
  private User user;
  private String description;

  public void updateUserData(String name, String email, String phone) {
    if (name != null) user.setName(name);
    if (email != null) user.setEmail(email);
    if (phone != null) user.setPhone(phone);
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
