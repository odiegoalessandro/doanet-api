package com.doanet.api.application.usecases.auth;

import com.doanet.api.application.commands.LogoutCommand;
import com.doanet.api.application.gateways.RefreshTokenRepository;
import com.doanet.api.application.gateways.TokenFactory;

public class LogoutUseCase {
  private final RefreshTokenRepository refreshTokenRepository;
  private final TokenFactory tokenFactory;

  public LogoutUseCase(RefreshTokenRepository refreshTokenRepository, TokenFactory tokenFactory) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.tokenFactory = tokenFactory;
  }

  public void execute(LogoutCommand command) {
    this.refreshTokenRepository.findByTokenHash(this.tokenFactory.hash(command.refreshToken()))
      .ifPresent(token -> {
        token.revoke();
        this.refreshTokenRepository.save(token);
      });
  }
}
