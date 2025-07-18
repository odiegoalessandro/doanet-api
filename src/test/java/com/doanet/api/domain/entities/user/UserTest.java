package com.doanet.api.domain.entities.user;

import com.doanet.api.legacy.enums.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
  @Test
  public void shouldThrowException_WhenNameIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> {
      new User(null,
        "",
        "joao.silva@empresa.com",
        "senha123",
        "11987654321",
        "Rua das Flores",
        "123",
        "Centro",
        "São Paulo",
        "SP",
        "01234-567",
        null,
        null,
        null,
        true);
    });
  }

  @Test
  public void shouldThrowException_WhenEmailIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      new User(null,
        "joão",
        null,
        "senha123",
        "11987654321",
        "Rua das Flores",
        "123",
        "Centro",
        "São Paulo",
        "SP",
        "01234-567",
        null,
        null,
        UserType.DONOR,
        true);
    });
  }

  @Test
  public void shouldThrowException_WhenEmailIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> {
      new User(null,
        "joão",
        "joao.d@empresa",
        "senha123",
        "11987654321",
        "Rua das Flores",
        "123",
        "Centro",
        "São Paulo",
        "SP",
        "01234-567",
        null,
        null,
        UserType.DONOR,
        true);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new User(null,
        "joão",
        "joao.dempresa.me",
        "senha123",
        "11987654321",
        "Rua das Flores",
        "123",
        "Centro",
        "São Paulo",
        "SP",
        "01234-567",
        null,
        null,
        UserType.DONOR,
        true);
    });
  }

  @Test
  void shouldThrowException_WhenPhoneIsTooShort() {
    assertThrows(IllegalArgumentException.class, () -> {
      new User(null,
        "Nome",
        "email@valido.com",
        "senha123",
        "1234",
        "Rua",
        "123",
        "Bairro",
        "Cidade",
        "SP",
        "01234-567",
        -23.0,
        -46.0,
        UserType.DONOR,
        true);
    });
  }

  @Test
  void shouldThrowException_WhenZipCodeIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> {
      new User(null,
        "Nome",
        "email@valido.com",
        "senha123",
        "11999999999",
        "Rua",
        "123",
        "Bairro",
        "Cidade",
        "SP",
        "123",
        -23.0,
        -46.0,
        UserType.DONOR,
        true);
    });
  }

  @Test
  void shouldThrowException_WhenUserTypeIsNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      new User(null,
        "Nome",
        "email@valido.com",
        "senha123",
        "11999999999",
        "Rua",
        "123",
        "Bairro",
        "Cidade",
        "SP",
        "01234-567",
        -23.0,
        -46.0,
        null,
        true);
    });
  }
}
