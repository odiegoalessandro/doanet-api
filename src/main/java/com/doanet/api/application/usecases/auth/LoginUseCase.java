package com.doanet.api.application.usecases.auth;

import com.doanet.api.application.commands.LoginCommand;
import com.doanet.api.application.dto.AuthTokens;
import com.doanet.api.application.exceptions.InvalidCredentialsException;
import com.doanet.api.application.gateways.AccessTokenIssuer;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.gateways.RefreshTokenRepository;
import com.doanet.api.application.gateways.TokenFactory;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.domain.entities.auth.RefreshToken;
import com.doanet.api.domain.entities.user.User;

import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

public class LoginUseCase {
  private static final String INVALID_CREDENTIALS_MESSAGE = "Email ou senha inválidos";

  private final UserRepository userRepository;
  private final PasswordHasher passwordHasher;
  private final AccessTokenIssuer accessTokenIssuer;
  private final RefreshTokenRepository refreshTokenRepository;
  private final TokenFactory tokenFactory;
  private final Duration refreshTokenTtl;

  public LoginUseCase(UserRepository userRepository,
                      PasswordHasher passwordHasher,
                      AccessTokenIssuer accessTokenIssuer,
                      RefreshTokenRepository refreshTokenRepository,
                      TokenFactory tokenFactory,
                      Duration refreshTokenTtl) {
    this.userRepository = userRepository;
    this.passwordHasher = passwordHasher;
    this.accessTokenIssuer = accessTokenIssuer;
    this.refreshTokenRepository = refreshTokenRepository;
    this.tokenFactory = tokenFactory;
    this.refreshTokenTtl = refreshTokenTtl;
  }

  public AuthTokens execute(LoginCommand command) {
    var user = this.userRepository.findByEmail(normalizeEmail(command.email()))
      .filter(User::isActive)
      .orElseThrow(() -> new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE));

    if (!this.passwordHasher.matches(command.password(), user.getPassword())) {
      throw new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
    }

    upgradeLegacyPasswordIfNeeded(user, command.password());

    return issueTokens(user);
  }

  private void upgradeLegacyPasswordIfNeeded(User user, String rawPassword) {
    if (this.passwordHasher.isHashed(user.getPassword())) {
      return;
    }

    user.setPassword(this.passwordHasher.hash(rawPassword));
    this.userRepository.save(user);
  }

  private AuthTokens issueTokens(User user) {
    var now = Instant.now();
    var rawRefreshToken = this.tokenFactory.generate();

    var refreshToken = new RefreshToken(
      null,
      user.getId(),
      this.tokenFactory.hash(rawRefreshToken),
      now.plus(this.refreshTokenTtl),
      false,
      now
    );
    this.refreshTokenRepository.save(refreshToken);

    var accessToken = this.accessTokenIssuer.issue(user);

    return new AuthTokens(accessToken, rawRefreshToken, this.accessTokenIssuer.accessTokenTtlSeconds());
  }

  private String normalizeEmail(String email) {
    return email == null ? null : email.trim().toLowerCase(Locale.ROOT);
  }
}
