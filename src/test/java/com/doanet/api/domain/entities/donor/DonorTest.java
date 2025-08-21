package com.doanet.api.domain.entities.donor;

import static org.junit.jupiter.api.Assertions.*;

import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;
import org.junit.jupiter.api.Test;

class DonorTest {

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
      UserType.DONOR,
      true
    );
  }

  @Test
  void shouldCreateDonorWithValidData() {
    var donor = new Donor(1L, validUser(), "12345678901", "Razão Social");
    assertEquals("Razão Social", donor.getReasonSocial());
    assertEquals("12345678901", donor.getDocument());
  }

  @Test
  void shouldThrow_WhenUserIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Donor(1L, null, "12345678901", "Razão Social"));
  }

  @Test
  void shouldThrow_WhenDocumentIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Donor(1L, validUser(), null, "Razão Social"));
  }

  @Test
  void shouldThrow_WhenDocumentIsBlank() {
    assertThrows(IllegalArgumentException.class, () ->
      new Donor(1L, validUser(), "  ", "Razão Social"));
  }

  @Test
  void shouldThrow_WhenReasonSocialIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Donor(1L, validUser(), "12345678901", null));
  }

  @Test
  void shouldThrow_WhenReasonSocialIsBlank() {
    assertThrows(IllegalArgumentException.class, () ->
      new Donor(1L, validUser(), "12345678901", "  "));
  }

  @Test
  void shouldUpdateUserDataCorrectly() {
    var donor = new Donor(1L, validUser(), "12345678901", "Razão Social");
    donor.updateUserData("Carlos", "carlos@example.com", "11988887777");
    assertEquals("Carlos", donor.getUser().getName());
    assertEquals("carlos@example.com", donor.getUser().getEmail());
    assertEquals("11988887777", donor.getUser().getPhone());
  }

  @Test
  void shouldThrow_WhenSetUserNull() {
    var donor = new Donor(1L, validUser(), "12345678901", "Razão Social");
    assertThrows(IllegalArgumentException.class, () -> donor.setUser(null));
  }

  @Test
  void shouldThrow_WhenSetDocumentNull() {
    var donor = new Donor(1L, validUser(), "12345678901", "Razão Social");
    assertThrows(IllegalArgumentException.class, () -> donor.setDocument(null));
  }

  @Test
  void shouldThrow_WhenSetReasonSocialNull() {
    var donor = new Donor(1L, validUser(), "12345678901", "Razão Social");
    assertThrows(IllegalArgumentException.class, () -> donor.setReasonSocial(null));
  }
}
