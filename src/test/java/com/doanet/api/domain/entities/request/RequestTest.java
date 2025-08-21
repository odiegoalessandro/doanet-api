package com.doanet.api.domain.entities.request;

import static org.junit.jupiter.api.Assertions.*;

import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.RequestStatus;
import com.doanet.api.domain.enums.UserType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class RequestTest {

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

  private DonationPoint validDonationPoint() {
    var dp = new DonationPoint(null, validUser(), "descrição");
    dp.setId(1L);
    return dp;
  }

  private Ong validOng() {
    var ong = new Ong(null, validUser(), "12312312313212");
    ong.setId(1L);
    return ong;
  }

  @Test
  void shouldConstructWithValidData() {
    var request = new Request(
      1L,
      validDonationPoint(),
      validOng(),
      LocalDate.now(),
      List.of(),
      RequestStatus.CREATED
    );
    assertNotNull(request);
    assertEquals(RequestStatus.CREATED, request.getStatus());
  }

  @Test
  void shouldThrow_WhenDonationPointIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Request(1L, null, validOng(), LocalDate.now(), List.of(), RequestStatus.CREATED));
  }

  @Test
  void shouldThrow_WhenOngIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Request(1L, validDonationPoint(), null, LocalDate.now(), List.of(), RequestStatus.CREATED));
  }

  @Test
  void shouldThrow_WhenStatusIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
      new Request(1L, validDonationPoint(), validOng(), LocalDate.now(), List.of(), null));
  }

  @Test
  void shouldSetValidStatus() {
    var request = new Request(
      1L,
      validDonationPoint(),
      validOng(),
      LocalDate.now(),
      List.of(),
      RequestStatus.CREATED
    );
    request.setStatus(RequestStatus.DELIVERED);
    assertEquals(RequestStatus.DELIVERED, request.getStatus());
  }

  @Test
  void shouldThrow_WhenSetStatusNull() {
    var request = new Request(
      1L,
      validDonationPoint(),
      validOng(),
      LocalDate.now(),
      List.of(),
      RequestStatus.CREATED
    );
    assertThrows(IllegalArgumentException.class, () -> request.setStatus(null));
  }
}
