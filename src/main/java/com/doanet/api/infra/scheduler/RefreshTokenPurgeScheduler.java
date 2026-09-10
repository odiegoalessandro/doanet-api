package com.doanet.api.infra.scheduler;

import com.doanet.api.application.usecases.auth.PurgeExpiredRefreshTokensUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Remove periodicamente os refresh tokens expirados ou já revogados,
 * mantendo a tabela enxuta.
 */
@Component
@ConditionalOnProperty(
  name = "security.refresh-token.purge.enabled",
  havingValue = "true",
  matchIfMissing = true
)
public class RefreshTokenPurgeScheduler {
  private final PurgeExpiredRefreshTokensUseCase purgeExpiredRefreshTokensUseCase;

  public RefreshTokenPurgeScheduler(PurgeExpiredRefreshTokensUseCase purgeExpiredRefreshTokensUseCase) {
    this.purgeExpiredRefreshTokensUseCase = purgeExpiredRefreshTokensUseCase;
  }

  @Scheduled(
    initialDelayString = "${security.refresh-token.purge.initial-delay-ms:60000}",
    fixedDelayString = "${security.refresh-token.purge.interval-ms:86400000}"
  )
  public void purgeExpiredTokens() {
    this.purgeExpiredRefreshTokensUseCase.execute();
  }
}
