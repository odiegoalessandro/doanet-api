package com.doanet.api.domain.entities.donor;

import com.doanet.api.domain.entities.user.User;

// TODO: criar validações de campo dentro do construtor
// TODO: criar testes unitarios na classe de dominio do doador
public class Donor {
  private Long id;
  private User user;
  private String document;
  private String reasonSocial;

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

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  public String getReasonSocial() {
    return reasonSocial;
  }

  public void setReasonSocial(String reasonSocial) {
    this.reasonSocial = reasonSocial;
  }

  public void updateUserData(String name, String email, String phone) {
    // TODO: criar validação desses campos antes de settar eles
    if (name != null) user.setName(name);
    if (email != null) user.setEmail(email);
    if (phone != null) user.setPhone(phone);
  }

  public Donor(Long id, User user, String document, String reasonSocial) {
    this.id = id;
    this.user = user;
    this.document = document;
    this.reasonSocial = reasonSocial;
  }
}
