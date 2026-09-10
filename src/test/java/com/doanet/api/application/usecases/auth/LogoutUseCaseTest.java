package com.doanet.api.application.usecases.auth;

import com.doanet.api.application.commands.LogoutCommand;
import com.doanet.api.application.gateways.RefreshTokenRepository;
import com.doanet.api.application.gateways.TokenFactory;
import com.doanet.api.domain.entities.auth.RefreshToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LogoutUseCaseTest {

  private static final String RAW_TOKEN = "raw-refresh";
  private static final String TOKEN_HASH = "hashed-refresh";

  private RefreshTokenRepository refreshTokenRepository;
  private TokenFactory tokenFactory;
  private LogoutUseCase logoutUseCase;

  @BeforeEach
  void setUp() {
    refreshTokenRepository = mock(RefreshTokenRepository.class);
    tokenFactory = mock(TokenFactory.class);
    logoutUseCase = new LogoutUseCase(refreshTokenRepository, tokenFactory);
  }

  @Test
  void shouldRevokeStoredToken_WhenItExists() {
    var token = new RefreshToken(
      null, 1L, TOKEN_HASH, Instant.now().plusSeconds(3600), false, Instant.now()
    );
    when(tokenFactory.hash(RAW_TOKEN)).thenReturn(TOKEN_HASH);
    when(refreshTokenRepository.findByTokenHash(TOKEN_HASH)).thenReturn(Optional.of(token));

    logoutUseCase.execute(new LogoutCommand(RAW_TOKEN));

    assertTrue(token.isRevoked());
    verify(refreshTokenRepository).save(token);
  }

  @Test
  void shouldDoNothing_WhenTokenIsNotStored() {
    when(tokenFactory.hash(RAW_TOKEN)).thenReturn(TOKEN_HASH);
    when(refreshTokenRepository.findByTokenHash(TOKEN_HASH)).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> logoutUseCase.execute(new LogoutCommand(RAW_TOKEN)));

    verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
  }
}
