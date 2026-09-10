package com.doanet.api.application.usecases.user;

import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GeolocatePendingUsersUseCaseTest {

  private UserRepository userRepository;
  private GeolocateUserUseCase geolocateUserUseCase;
  private GeolocatePendingUsersUseCase geolocatePendingUsersUseCase;

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    geolocateUserUseCase = mock(GeolocateUserUseCase.class);
    geolocatePendingUsersUseCase = new GeolocatePendingUsersUseCase(userRepository, geolocateUserUseCase);
  }

  private User createPendingUser(String email) {
    return new User(
      null,
      "João",
      email,
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
  void shouldPersistOnlyTheUsersThatWereGeolocated() {
    var stillPending = createPendingUser("pendente@email.com");
    var geolocated = createPendingUser("geolocalizado@email.com");

    when(userRepository.findAllWithoutCoordinates()).thenReturn(List.of(stillPending, geolocated));
    when(geolocateUserUseCase.execute(stillPending)).thenReturn(false);
    when(geolocateUserUseCase.execute(geolocated)).thenReturn(true);

    var total = geolocatePendingUsersUseCase.execute();

    assertEquals(1, total);
    verify(userRepository, never()).save(stillPending);
    verify(userRepository).save(geolocated);
  }

  @Test
  void shouldDoNothing_WhenThereAreNoPendingUsers() {
    when(userRepository.findAllWithoutCoordinates()).thenReturn(List.of());

    var total = geolocatePendingUsersUseCase.execute();

    assertEquals(0, total);
    verify(userRepository, never()).save(any());
    verifyNoInteractions(geolocateUserUseCase);
  }

  @Test
  void shouldNotPersistAnything_WhenNoUserCouldBeGeolocated() {
    var pending = createPendingUser("pendente@email.com");
    when(userRepository.findAllWithoutCoordinates()).thenReturn(List.of(pending));
    when(geolocateUserUseCase.execute(pending)).thenReturn(false);

    var total = geolocatePendingUsersUseCase.execute();

    assertEquals(0, total);
    verify(userRepository, never()).save(any());
  }
}
