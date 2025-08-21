package com.doanet.api.domain.entities.donationpoint;

import static org.junit.jupiter.api.Assertions.*;

import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;
import org.junit.jupiter.api.Test;

class DonationPointTest {

  private User validUser() {
    return new User(
      1L,
      "João",
      "joao@example.com",
      "senha123",
      "11999999999",
      "Rua A",
      "123",
      "Centro",
      "São Paulo",
      "SP",
      "01234-567",
      null,
      null,
      UserType.DONATION_POINT,
      true
    );
  }

  @Test
  void shouldConstructWithValidData() {
    var dp = new DonationPoint(1L, validUser(), "Descrição do ponto");
    assertEquals("Descrição do ponto", dp.getDescription());
    assertEquals("João", dp.getUser().getName());
  }

  @Test
  void shouldThrow_WhenUserIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new DonationPoint(1L, null, "Descrição"));
  }

  @Test
  void shouldThrow_WhenDescriptionIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new DonationPoint(1L, validUser(), null));
  }

  @Test
  void shouldThrow_WhenDescriptionIsEmpty() {
    assertThrows(IllegalArgumentException.class, () ->
      new DonationPoint(1L, validUser(), "  "));
  }

  @Test
  void shouldUpdateUserData() {
    var dp = new DonationPoint(1L, validUser(), "Descrição");
    dp.updateUserData("Carlos", "carlos@example.com", "11988887777", "Novo desc");
    assertEquals("Carlos", dp.getUser().getName());
    assertEquals("carlos@example.com", dp.getUser().getEmail());
    assertEquals("11988887777", dp.getUser().getPhone());
    assertEquals("Novo desc", dp.getDescription());
  }

  @Test
  void shouldThrow_WhenSetDescriptionNull() {
    var dp = new DonationPoint(1L, validUser(), "Descrição");
    assertThrows(IllegalArgumentException.class, () -> dp.setDescription(null));
  }

  @Test
  void shouldThrow_WhenSetDescriptionEmpty() {
    var dp = new DonationPoint(1L, validUser(), "Descrição");
    assertThrows(IllegalArgumentException.class, () -> dp.setDescription("  "));
  }

  @Test
  void shouldThrow_WhenSetUserNull() {
    var dp = new DonationPoint(1L, validUser(), "Descrição");
    assertThrows(IllegalArgumentException.class, () -> dp.setUser(null));
  }
}
