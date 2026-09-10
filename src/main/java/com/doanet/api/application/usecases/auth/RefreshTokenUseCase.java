package com.doanet.api.application.usecases.auth;

import com.doanet.api.application.commands.RefreshTokenCommand;
import com.doanet.api.application.dto.AuthTokens;
import com.doanet.api.application.exceptions.InvalidTokenException;
import com.doanet.api.application.gateways.AccessTokenIssuer;
import com.doanet.api.application.gateways.RefreshTokenRepository;
import com.doanet.api.application.gateways.TokenFactory;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.domain.entities.auth.RefreshToken;
import com.doanet.api.domain.entities.user.User;

import java.time.Duration;
import java.time.Instant;

public class RefreshTokenUseCase {
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  private final AccessTokenIssuer accessTokenIssuer;
  private final TokenFactory tokenFactory;
  private final Duration refreshTokenTtl;

  public RefreshTokenUseCase(RefreshTokenRepository refreshTokenRepository,
                             UserRepository userRepository,
                             AccessTokenIssuer accessTokenIssuer,
                             TokenFactory tokenFactory,
                             Duration refreshTokenTtl) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
    this.accessTokenIssuer = accessTokenIssuer;
    this.tokenFactory = tokenFactory;
    this.refreshTokenTtl = refreshTokenTtl;
  }

  public AuthTokens execute(RefreshTokenCommand command) {
    var tokenHash = this.tokenFactory.hash(command.refreshToken());
    var storedToken = this.refreshTokenRepository.findByTokenHash(tokenHash)
      .orElseThrow(() -> new InvalidTokenException("Refresh token inválido"));

    if (storedToken.isRevoked()) {
      this.refreshTokenRepository.revokeAllByUserId(storedToken.getUserId());
      throw new InvalidTokenException("Refresh token reutilizado, faça login novamente");
    }

    if (storedToken.isExpired(Instant.now())) {
      throw new InvalidTokenException("Refresh token expirado, faça login novamente");
    }

    var user = this.userRepository.findById(storedToken.getUserId())
      .filter(User::isActive)
      .orElseThrow(() -> new InvalidTokenException("Usuário inválido"));

    storedToken.revoke();
    this.refreshTokenRepository.save(storedToken);

    var now = Instant.now();
    var rawRefreshToken = this.tokenFactory.generate();
    var rotatedToken = new RefreshToken(
      null,
      user.getId(),
      this.tokenFactory.hash(rawRefreshToken),
      now.plus(this.refreshTokenTtl),
      false,
      now
    );
    this.refreshTokenRepository.save(rotatedToken);

    var accessToken = this.accessTokenIssuer.issue(user);

    return new AuthTokens(accessToken, rawRefreshToken, this.accessTokenIssuer.accessTokenTtlSeconds());
  }
}
