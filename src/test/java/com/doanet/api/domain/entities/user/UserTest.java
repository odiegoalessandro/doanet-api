package com.doanet.api.domain.entities.user;

import com.doanet.api.domain.enums.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  private User createValidUser() {
    return new User(
      1L,
      "João",
      "joao@email.com",
      "senha123",
      "11999999999",
      "Rua A",
      "123",
      "Centro",
      "São Paulo",
      "SP",
      "12345-678",
      -23.5,
      -46.6,
      UserType.DONOR,
      true
    );
  }

  @Test
  void shouldCreateUser_WhenDataIsValid() {
    var user = createValidUser();
    assertEquals("João", user.getName());
    assertEquals("joao@email.com", user.getEmail());
  }

  @Test
  void shouldThrow_WhenNameIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setName(" "));
  }

  @Test
  void shouldThrow_WhenEmailIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setEmail("invalido"));
  }

  @Test
  void shouldThrow_WhenPhoneIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setPhone("123"));
  }

  @Test
  void shouldThrow_WhenZipCodeIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setZipCode("abc123"));
  }

  @Test
  void shouldThrow_WhenUserTypeIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new User(
        1L, "João", "joao@email.com", "senha123", "11999999999",
        "Rua A", "123", "Centro", "SP", "SP", "12345-678",
        null, null, null, true
      )
    );
  }

  @Test
  void shouldThrow_WhenPasswordIsShort() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setPassword("123"));
  }

  @Test
  void shouldThrow_WhenStreetIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setStreet(" "));
  }

  @Test
  void shouldThrow_WhenNumberIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setNumber(""));
  }

  @Test
  void shouldThrow_WhenNeighborhoodIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setNeighborhood(null));
  }

  @Test
  void shouldThrow_WhenCityIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setCity(" "));
  }

  @Test
  void shouldThrow_WhenStateIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> createValidUser().setState(""));
  }

  @Test
  void shouldBuildAddressCorrectly() {
    var user = createValidUser();
    assertEquals("Rua A, 123, Centro, São Paulo, SP, Brazil, 12345-678", user.buildAddress());
  }
}
