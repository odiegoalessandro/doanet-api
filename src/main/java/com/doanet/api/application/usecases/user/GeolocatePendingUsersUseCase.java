package com.doanet.api.application.usecases.user;

import com.doanet.api.application.gateways.UserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Backfill de geolocalização: varre os usuários ativos que ficaram sem
 * coordenadas (por indisponibilidade do OpenCage no momento do cadastro)
 * e tenta preenchê-las novamente.
 */
@Slf4j
public class GeolocatePendingUsersUseCase {
  private final UserRepository userRepository;
  private final GeolocateUserUseCase geolocateUserUseCase;

  public GeolocatePendingUsersUseCase(
    UserRepository userRepository,
    GeolocateUserUseCase geolocateUserUseCase
  ) {
    this.userRepository = userRepository;
    this.geolocateUserUseCase = geolocateUserUseCase;
  }

  public int execute() {
    var pendingUsers = this.userRepository.findAllWithoutCoordinates();
    var geolocated = 0;

    for (var user : pendingUsers) {
      if (this.geolocateUserUseCase.execute(user)) {
        this.userRepository.save(user);
        geolocated++;
      }
    }

    if (geolocated > 0) {
      log.info("Backfill de geolocalização preencheu as coordenadas de {} usuário(s).", geolocated);
    }

    return geolocated;
  }
}
