package com.doanet.api.domain.entities.ong;

import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OngTest {

  private User validUser() {
    return new User(
      1L,
      "Maria",
      "maria@example.com",
      "senha123",
      "11999999999",
      "Av Brasil",
      "456",
      "Bairro Legal",
      "Rio de Janeiro",
      "RJ",
      "12345-678",
      null,
      null,
      UserType.ONG,
      true
    );
  }

  @Test
  void shouldCreateValidOng() {
    var ong = new Ong(1L, validUser(), "12345678000199");
    assertEquals("12345678000199", ong.getCnpj());
    assertEquals("Maria", ong.getUser().getName());
  }

  @Test
  void shouldThrow_WhenUserIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Ong(1L, null, "12345678000199"));
  }

  @Test
  void shouldThrow_WhenCnpjIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Ong(1L, validUser(), null));
  }

  @Test
  void shouldThrow_WhenCnpjIsBlank() {
    assertThrows(IllegalArgumentException.class, () ->
      new Ong(1L, validUser(), " "));
  }

  @Test
  void shouldThrow_WhenCnpjIsInvalidLength() {
    assertThrows(IllegalArgumentException.class, () ->
      new Ong(1L, validUser(), "123"));
  }

  @Test
  void shouldThrow_WhenCnpjHasNonDigits() {
    assertThrows(IllegalArgumentException.class, () ->
      new Ong(1L, validUser(), "12345678A00199"));
  }

  @Test
  void shouldThrow_WhenSetUserIsNull() {
    var ong = new Ong(1L, validUser(), "12345678000199");
    assertThrows(IllegalArgumentException.class, () -> ong.setUser(null));
  }

  @Test
  void shouldThrow_WhenSetCnpjIsInvalid() {
    var ong = new Ong(1L, validUser(), "12345678000199");
    assertThrows(IllegalArgumentException.class, () -> ong.setCnpj("123"));
  }

  @Test
  void shouldUpdateUserData() {
    var ong = new Ong(1L, validUser(), "12345678000199");
    ong.updateUserData("Nova ONG", "nova@ong.com", "11988887777");

    assertEquals("Nova ONG", ong.getUser().getName());
    assertEquals("nova@ong.com", ong.getUser().getEmail());
    assertEquals("11988887777", ong.getUser().getPhone());
  }
}
