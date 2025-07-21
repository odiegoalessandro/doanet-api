package com.doanet.api.domain.entities.user;


import java.time.LocalDate;

public class Item {
  private Long id;
  private String name;
  private String description;
  private boolean isPerishable;
  private LocalDate expirationDate;

  public Item(Long id, String name, String description, boolean isPerishable, LocalDate expirationDate) {
    if(name == null || name.trim().isEmpty()){
      throw new IllegalArgumentException("O nome não pode ser nulo e deve conter 1 ou mais caracteres");
    }

    if (isPerishable) {
      if (expirationDate == null) {
        throw new IllegalArgumentException("Data de validade é obrigatória para produto perecível");
      }
      if (!expirationDate.isAfter(LocalDate.now()) && !expirationDate.isEqual(LocalDate.now())) {
        throw new IllegalArgumentException("Data de validade não pode ser passada");
      }
    } else {
      if (expirationDate != null) {
        throw new IllegalArgumentException("Não é possível definir data de validade para produto não perecível");
      }
    }

    this.id = id;
    this.name = name;
    this.description = description;
    this.isPerishable = isPerishable;
    this.expirationDate = expirationDate;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean isPerishable() {
    return isPerishable;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }
}

