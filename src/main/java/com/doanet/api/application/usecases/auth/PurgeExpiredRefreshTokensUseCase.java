package com.doanet.api.application.usecases.auth;

import com.doanet.api.application.gateways.RefreshTokenRepository;

import java.time.Instant;

public class PurgeExpiredRefreshTokensUseCase {
  private final RefreshTokenRepository refreshTokenRepository;

  public PurgeExpiredRefreshTokensUseCase(RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public int execute() {
    return this.refreshTokenRepository.deleteExpired(Instant.now());
  }
}
