package com.doanet.api.application.usecases.user;

import com.doanet.api.application.dto.Coordinates;
import com.doanet.api.application.exceptions.CoordinatesInternalServerException;
import com.doanet.api.application.exceptions.CoordinatesNotFoundException;
import com.doanet.api.application.gateways.GetCoordinatesByAddress;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeolocateUserUseCaseTest {

  private GetCoordinatesByAddress getCoordinatesByAddress;
  private GeolocateUserUseCase geolocateUserUseCase;

  @BeforeEach
  void setUp() {
    getCoordinatesByAddress = mock(GetCoordinatesByAddress.class);
    geolocateUserUseCase = new GeolocateUserUseCase(getCoordinatesByAddress);
  }

  private User createUserWithoutCoordinates() {
    return new User(
      null,
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
      null,
      null,
      UserType.DONOR,
      true
    );
  }

  @Test
  void shouldFillCoordinates_WhenGatewayReturnsCoordinates() {
    var user = createUserWithoutCoordinates();
    when(getCoordinatesByAddress.execute(user.buildAddress()))
      .thenReturn(new Coordinates(-23.5, -46.6));

    var geolocated = geolocateUserUseCase.execute(user);

    assertTrue(geolocated);
    assertEquals(-23.5, user.getLatitude().doubleValue());
    assertEquals(-46.6, user.getLongitude().doubleValue());
  }

  @Test
  void shouldSendTheUserAddressToTheGateway() {
    var user = createUserWithoutCoordinates();
    when(getCoordinatesByAddress.execute(anyString()))
      .thenReturn(new Coordinates(-23.5, -46.6));

    geolocateUserUseCase.execute(user);

    verify(getCoordinatesByAddress).execute(
      "Rua A, 123, Centro, São Paulo, SP, Brazil, 12345-678"
    );
  }

  @Test
  void shouldNotFillCoordinatesAndNotBreakTheFlow_WhenAddressIsNotFound() {
    var user = createUserWithoutCoordinates();
    when(getCoordinatesByAddress.execute(anyString()))
      .thenThrow(new CoordinatesNotFoundException("Nenhuma coordenada encontrada."));

    var geolocated = geolocateUserUseCase.execute(user);

    assertFalse(geolocated);
    assertNull(user.getLatitude());
    assertNull(user.getLongitude());
  }

  @Test
  void shouldNotFillCoordinatesAndNotBreakTheFlow_WhenGatewayFails() {
    var user = createUserWithoutCoordinates();
    when(getCoordinatesByAddress.execute(anyString()))
      .thenThrow(new CoordinatesInternalServerException("Erro ao chamar API de geolocalização"));

    var geolocated = geolocateUserUseCase.execute(user);

    assertFalse(geolocated);
    assertNull(user.getLatitude());
    assertNull(user.getLongitude());
  }

  @Test
  void shouldNotFillCoordinates_WhenGatewayReturnsIncompleteCoordinates() {
    var user = createUserWithoutCoordinates();
    when(getCoordinatesByAddress.execute(anyString()))
      .thenReturn(new Coordinates(null, null));

    var geolocated = geolocateUserUseCase.execute(user);

    assertFalse(geolocated);
    assertNull(user.getLatitude());
    assertNull(user.getLongitude());
  }
}
