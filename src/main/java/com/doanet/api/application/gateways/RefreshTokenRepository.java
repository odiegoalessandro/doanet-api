package com.doanet.api.application.gateways;

import com.doanet.api.domain.entities.auth.RefreshToken;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository {
  RefreshToken save(RefreshToken refreshToken);

  Optional<RefreshToken> findByTokenHash(String tokenHash);

  void revokeAllByUserId(Long userId);

  int deleteExpired(Instant now);
}
