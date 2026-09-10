package com.doanet.api.infra.scheduler;

import com.doanet.api.application.usecases.user.GeolocatePendingUsersUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Dispara periodicamente o backfill de coordenadas dos usuários que foram
 * cadastrados enquanto o OpenCage estava indisponível.
 */
@Component
@ConditionalOnProperty(
  name = "geolocation.backfill.enabled",
  havingValue = "true",
  matchIfMissing = true
)
public class GeolocationBackfillScheduler {
  private final GeolocatePendingUsersUseCase geolocatePendingUsersUseCase;

  public GeolocationBackfillScheduler(GeolocatePendingUsersUseCase geolocatePendingUsersUseCase) {
    this.geolocatePendingUsersUseCase = geolocatePendingUsersUseCase;
  }

  @Scheduled(
    initialDelayString = "${geolocation.backfill.initial-delay-ms:60000}",
    fixedDelayString = "${geolocation.backfill.interval-ms:3600000}"
  )
  public void backfillPendingCoordinates() {
    this.geolocatePendingUsersUseCase.execute();
  }
}
