package com.doanet.api.domain.entities.ong;

import com.doanet.api.domain.entities.user.User;

public class Ong {
  private Long id;
  private User user;
  private String cnpj;

  public Ong(Long id, User user, String cnpj) {
    validateCnpj(cnpj);
    validateUser(user);

    this.id = id;
    this.user = user;
    this.cnpj = cnpj;
  }

  public void updateUserData(String name, String email, String phone) {
    if (name != null) user.setName(name);
    if (email != null) user.setEmail(email);
    if (phone != null) user.setPhone(phone);
  }

  private void validateCnpj(String cnpj) {
    if (cnpj == null || cnpj.trim().isEmpty()) {
      throw new IllegalArgumentException("O CNPJ não pode ser nulo ou vazio");
    }

    if(cnpj.length() != 14 || !cnpj.matches("\\d{14}")) {
      throw new IllegalArgumentException("CNPJ inválido");
    }
  }

  private void validateUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("Usuário não pode ser nulo");
    }
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUser(User user) {
    validateUser(user);
    this.user = user;
  }

  public void setCnpj(String cnpj) {
    validateCnpj(cnpj);
    this.cnpj = cnpj;
  }

  public Long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public String getCnpj() {
    return cnpj;
  }
}
