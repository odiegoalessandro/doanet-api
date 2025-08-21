package com.doanet.api.domain.entities.donor;

import com.doanet.api.domain.entities.user.User;

public class Donor {
  private Long id;
  private User user;

  // TODO: no futuro será necessario criar uma abordagem melhor para os documentos(CPF, CNPJ)
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
    validadeUser(user);
    this.user = user;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    validadeDocument(document);
    this.document = document;
  }

  public String getReasonSocial() {
    return reasonSocial;
  }

  public void setReasonSocial(String reasonSocial) {
    validadeReasonSocial(reasonSocial);
    this.reasonSocial = reasonSocial;
  }

  public void updateUserData(String name, String email, String phone) {
    if (name != null) user.setName(name);
    if (email != null) user.setEmail(email);
    if (phone != null) user.setPhone(phone);
  }

  public Donor(Long id, User user, String document, String reasonSocial) {
    validadeDocument(document);
    validadeReasonSocial(reasonSocial);
    validadeUser(user);

    this.id = id;
    this.user = user;
    this.document = document;
    this.reasonSocial = reasonSocial;
  }

  private void validadeReasonSocial(String reasonSocial) {
    if (reasonSocial == null || reasonSocial.trim().isEmpty()) {
      throw new IllegalArgumentException("A razão social não pode ser nula ou vazia");
    }
  }

  private void validadeDocument(String document) {

    if (document == null || document.trim().isEmpty()) {
      throw new IllegalArgumentException("O documento não pode ser nulo ou vazio");
    }
  }

  private void validadeUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("O usuário não pode ser nulo");
    }
  }
}
