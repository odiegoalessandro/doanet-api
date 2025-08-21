package com.doanet.api.domain.entities.donation;

import static org.junit.jupiter.api.Assertions.*;

import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.domain.entities.donor.Donor;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.DonationStatus;
import com.doanet.api.domain.enums.UserType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DonationTest {

  private User validUser() {
    return new User(
      1L,
      "João Silva",
      "joao.silva@example.com",
      "senhaSegura123",
      "11999999999",
      "Rua das Flores",
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

  private Donor validDonor() {
    var donor = new Donor(null, validUser(), "12312312312312", "razão social");
    donor.setId(1L);
    return donor;
  }

  private DonationPoint validPoint() {
    var dp = new DonationPoint(null, validUser(), "descrição");
    dp.setId(1L);
    return dp;
  }

  @Test
  void shouldConstructWithValidData() {
    var donation = new Donation(
      1L,
      validDonor(),
      validPoint(),
      LocalDate.now(),
      List.of(),
      DonationStatus.CREATED
    );
    assertNotNull(donation);
    assertEquals(DonationStatus.CREATED, donation.getStatus());
  }

  @Test
  void shouldThrow_WhenDonorIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Donation(1L, null, validPoint(), LocalDate.now(), List.of(), DonationStatus.CREATED));
  }

  @Test
  void shouldThrow_WhenDonorIdIsNull() {
    var donor = new Donor(null, validUser(), "12312312312312", "razão social");
    assertThrows(IllegalArgumentException.class, () ->
      new Donation(1L, donor, validPoint(), LocalDate.now(), List.of(), DonationStatus.CREATED));
  }

  @Test
  void shouldThrow_WhenDonationPointIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Donation(1L, validDonor(), null, LocalDate.now(), List.of(), DonationStatus.CREATED));
  }

  @Test
  void shouldThrow_WhenDonationPointIdIsNull() {
    var dp = new DonationPoint(null, validUser(), "descrição");
    assertThrows(IllegalArgumentException.class, () ->
      new Donation(1L, validDonor(), dp, LocalDate.now(), List.of(), DonationStatus.CREATED));
  }

  @Test
  void shouldThrow_WhenStatusIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Donation(1L, validDonor(), validPoint(), LocalDate.now(), List.of(), null));
  }

  @Test
  void shouldSetValidStatus() {
    var donation = new Donation(1L, validDonor(), validPoint(), LocalDate.now(), List.of(), DonationStatus.CREATED);
    donation.setStatus(DonationStatus.DELIVERED);
    assertEquals(DonationStatus.DELIVERED, donation.getStatus());
  }

  @Test
  void shouldThrow_WhenSetStatusNull() {
    var donation = new Donation(1L, validDonor(), validPoint(), LocalDate.now(), List.of(), DonationStatus.CREATED);
    assertThrows(IllegalArgumentException.class, () -> donation.setStatus(null));
  }
}
