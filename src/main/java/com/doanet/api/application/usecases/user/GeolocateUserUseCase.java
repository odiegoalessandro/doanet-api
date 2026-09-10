package com.doanet.api.application.usecases.user;

import com.doanet.api.application.exceptions.CoordinatesInternalServerException;
import com.doanet.api.application.exceptions.CoordinatesNotFoundException;
import com.doanet.api.application.gateways.GetCoordinatesByAddress;
import com.doanet.api.domain.entities.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeolocateUserUseCase {
  private final GetCoordinatesByAddress getCoordinatesByAddress;

  public GeolocateUserUseCase(GetCoordinatesByAddress getCoordinatesByAddress) {
    this.getCoordinatesByAddress = getCoordinatesByAddress;
  }

  /**
   * Preenche latitude/longitude do usuário a partir do endereço de forma best-effort:
   * se a geolocalização falhar, o usuário permanece sem coordenadas e é preenchido
   * depois pelo backfill (GeolocatePendingUsersUseCase).
   *
   * @return true se as coordenadas foram preenchidas.
   */
  public boolean execute(User user) {
    try {
      var coordinates = getCoordinatesByAddress.execute(user.buildAddress());

      if (coordinates.latitude() == null || coordinates.longitude() == null) {
        return false;
      }

      user.setLatitude(coordinates.latitude());
      user.setLongitude(coordinates.longitude());

      return true;
    } catch (CoordinatesNotFoundException | CoordinatesInternalServerException ex) {
      log.warn(
        "Não foi possível geolocalizar o usuário {}: {}. As coordenadas serão preenchidas pelo backfill.",
        user.getEmail(),
        ex.getMessage()
      );

      return false;
    }
  }
}
