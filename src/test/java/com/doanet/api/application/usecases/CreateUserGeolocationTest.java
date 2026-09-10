package com.doanet.api.application.usecases;

import com.doanet.api.application.commands.CreateDonationPointCommand;
import com.doanet.api.application.commands.CreateDonorCommand;
import com.doanet.api.application.commands.CreateOngCommand;
import com.doanet.api.application.dto.Coordinates;
import com.doanet.api.application.exceptions.CoordinatesInternalServerException;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.application.gateways.GetCoordinatesByAddress;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.donationpoint.CreateDonationPointUseCase;
import com.doanet.api.application.usecases.donor.CreateDonorUseCase;
import com.doanet.api.application.usecases.ong.CreateOngUseCase;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.domain.entities.donor.Donor;
import com.doanet.api.domain.entities.ong.Ong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CreateUserGeolocationTest {

  private GetCoordinatesByAddress getCoordinatesByAddress;
  private GeolocateUserUseCase geolocateUserUseCase;
  private PasswordHasher passwordHasher;
  private RecordAuditUseCase recordAuditUseCase;

  @BeforeEach
  void setUp() {
    getCoordinatesByAddress = mock(GetCoordinatesByAddress.class);
    geolocateUserUseCase = new GeolocateUserUseCase(getCoordinatesByAddress);
    passwordHasher = mock(PasswordHasher.class);
    when(passwordHasher.hash(anyString())).thenReturn("{bcrypt}hashed-password");
    recordAuditUseCase = mock(RecordAuditUseCase.class);
  }

  private void stubCoordinates() {
    when(getCoordinatesByAddress.execute(anyString()))
      .thenReturn(new Coordinates(-23.5, -46.6));
  }

  @Test
  void donorShouldBeSavedWithCoordinates() {
    var donorRepository = mock(DonorRepository.class);
    when(donorRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    stubCoordinates();

    var command = new CreateDonorCommand(
      "João", "joao@email.com", "senha123", "11999999999",
      "Rua A", "123", "Centro", "São Paulo", "SP", "12345-678",
      "João ME", "12345678900"
    );

    new CreateDonorUseCase(donorRepository, geolocateUserUseCase, passwordHasher, recordAuditUseCase).execute(command);

    var captor = ArgumentCaptor.forClass(Donor.class);
    verify(donorRepository).save(captor.capture());
    assertEquals(-23.5, captor.getValue().getUser().getLatitude().doubleValue());
    assertEquals(-46.6, captor.getValue().getUser().getLongitude().doubleValue());
  }

  @Test
  void ongShouldBeSavedWithCoordinates() {
    var ongRepository = mock(OngRepository.class);
    when(ongRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    stubCoordinates();

    var command = new CreateOngCommand(
      "Ong Amiga", "ong@email.com", "senha123", "11999999999",
      "Rua A", "123", "Centro", "São Paulo", "SP", "12345-678",
      "12345678000199"
    );

    new CreateOngUseCase(ongRepository, geolocateUserUseCase, passwordHasher, recordAuditUseCase).execute(command);

    var captor = ArgumentCaptor.forClass(Ong.class);
    verify(ongRepository).save(captor.capture());
    assertEquals(-23.5, captor.getValue().getUser().getLatitude().doubleValue());
    assertEquals(-46.6, captor.getValue().getUser().getLongitude().doubleValue());
  }

  @Test
  void donationPointShouldBeSavedWithCoordinates() {
    var donationPointRepository = mock(DonationPointRepository.class);
    when(donationPointRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    stubCoordinates();

    var command = new CreateDonationPointCommand(
      "Ponto Centro", "ponto@email.com", "senha123", "11999999999",
      "Rua A", "123", "Centro", "São Paulo", "SP", "12345-678",
      "Ponto de coleta do centro"
    );

    new CreateDonationPointUseCase(donationPointRepository, geolocateUserUseCase, passwordHasher, recordAuditUseCase).execute(command);

    var captor = ArgumentCaptor.forClass(DonationPoint.class);
    verify(donationPointRepository).save(captor.capture());
    assertEquals(-23.5, captor.getValue().getUser().getLatitude().doubleValue());
    assertEquals(-46.6, captor.getValue().getUser().getLongitude().doubleValue());
  }

  @Test
  void donorShouldStillBeCreated_WhenGeolocationIsUnavailable() {
    var donorRepository = mock(DonorRepository.class);
    when(donorRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    when(getCoordinatesByAddress.execute(anyString()))
      .thenThrow(new CoordinatesInternalServerException("Erro ao chamar API de geolocalização"));

    var command = new CreateDonorCommand(
      "João", "joao@email.com", "senha123", "11999999999",
      "Rua A", "123", "Centro", "São Paulo", "SP", "12345-678",
      "João ME", "12345678900"
    );

    var donor = new CreateDonorUseCase(donorRepository, geolocateUserUseCase, passwordHasher, recordAuditUseCase).execute(command);

    assertNotNull(donor);
    assertNull(donor.getUser().getLatitude());
    assertNull(donor.getUser().getLongitude());
    verify(donorRepository).save(any());
  }
}
