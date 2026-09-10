package com.doanet.api.application.usecases.auth;

import com.doanet.api.application.commands.RefreshTokenCommand;
import com.doanet.api.application.exceptions.InvalidTokenException;
import com.doanet.api.application.gateways.AccessTokenIssuer;
import com.doanet.api.application.gateways.RefreshTokenRepository;
import com.doanet.api.application.gateways.TokenFactory;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.domain.entities.auth.RefreshToken;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RefreshTokenUseCaseTest {

  private static final Long USER_ID = 2L;
  private static final Duration REFRESH_TTL = Duration.ofDays(7);
  private static final String RAW_TOKEN = "raw-refresh";
  private static final String TOKEN_HASH = "hashed-refresh";

  private RefreshTokenRepository refreshTokenRepository;
  private UserRepository userRepository;
  private AccessTokenIssuer accessTokenIssuer;
  private TokenFactory tokenFactory;
  private RefreshTokenUseCase refreshTokenUseCase;

  @BeforeEach
  void setUp() {
    refreshTokenRepository = mock(RefreshTokenRepository.class);
    userRepository = mock(UserRepository.class);
    accessTokenIssuer = mock(AccessTokenIssuer.class);
    tokenFactory = mock(TokenFactory.class);
    refreshTokenUseCase = new RefreshTokenUseCase(
      refreshTokenRepository,
      userRepository,
      accessTokenIssuer,
      tokenFactory,
      REFRESH_TTL
    );
  }

  private RefreshToken storedToken(boolean revoked, Instant expiresAt) {
    return new RefreshToken(null, USER_ID, TOKEN_HASH, expiresAt, revoked, Instant.now());
  }

  private User activeUser() {
    var user = new User(
      null, "João", "joao@email.com", "{bcrypt}hash", "11999999999",
      "Rua A", "123", "Centro", "São Paulo", "SP", "12345-678",
      null, null, UserType.DONOR, true
    );
    user.setId(USER_ID);
    return user;
  }

  private void stubTokenLookup(RefreshToken token) {
    when(tokenFactory.hash(RAW_TOKEN)).thenReturn(TOKEN_HASH);
    when(refreshTokenRepository.findByTokenHash(TOKEN_HASH)).thenReturn(Optional.of(token));
  }

  @Test
  void shouldRotateTokens_WhenRefreshTokenIsValid() {
    var current = storedToken(false, Instant.now().plus(REFRESH_TTL));
    stubTokenLookup(current);
    when(userRepository.findById(USER_ID)).thenReturn(Optional.of(activeUser()));
    when(tokenFactory.generate()).thenReturn("new-raw-refresh");
    when(tokenFactory.hash("new-raw-refresh")).thenReturn("new-hashed-refresh");
    when(accessTokenIssuer.issue(any(User.class))).thenReturn("new-access-token");
    when(accessTokenIssuer.accessTokenTtlSeconds()).thenReturn(900L);

    var tokens = refreshTokenUseCase.execute(new RefreshTokenCommand(RAW_TOKEN));

    assertEquals("new-access-token", tokens.accessToken());
    assertEquals("new-raw-refresh", tokens.refreshToken());
    assertEquals("Bearer", tokens.tokenType());
    assertEquals(900L, tokens.expiresIn());
    assertTrue(current.isRevoked());
  }

  @Test
  void shouldRevokeOldTokenAndPersistTheRotatedOne() {
    var current = storedToken(false, Instant.now().plus(REFRESH_TTL));
    stubTokenLookup(current);
    when(userRepository.findById(USER_ID)).thenReturn(Optional.of(activeUser()));
    when(tokenFactory.generate()).thenReturn("new-raw-refresh");
    when(tokenFactory.hash("new-raw-refresh")).thenReturn("new-hashed-refresh");
    when(accessTokenIssuer.issue(any(User.class))).thenReturn("new-access-token");
    when(accessTokenIssuer.accessTokenTtlSeconds()).thenReturn(900L);

    refreshTokenUseCase.execute(new RefreshTokenCommand(RAW_TOKEN));

    var captor = ArgumentCaptor.forClass(RefreshToken.class);
    verify(refreshTokenRepository, times(2)).save(captor.capture());
    var saved = captor.getAllValues();

    assertTrue(saved.get(0).isRevoked());
    assertEquals(TOKEN_HASH, saved.get(0).getTokenHash());
    assertFalse(saved.get(1).isRevoked());
    assertEquals("new-hashed-refresh", saved.get(1).getTokenHash());
    assertEquals(USER_ID, saved.get(1).getUserId());
  }

  @Test
  void shouldThrowInvalidToken_WhenTokenIsUnknown() {
    when(tokenFactory.hash(RAW_TOKEN)).thenReturn(TOKEN_HASH);
    when(refreshTokenRepository.findByTokenHash(TOKEN_HASH)).thenReturn(Optional.empty());

    var exception = assertThrows(
      InvalidTokenException.class,
      () -> refreshTokenUseCase.execute(new RefreshTokenCommand(RAW_TOKEN))
    );

    assertEquals("Refresh token inválido", exception.getMessage());
    verifyNoInteractions(userRepository, accessTokenIssuer);
    verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
  }

  @Test
  void shouldRevokeEverySession_WhenARevokedTokenIsReused() {
    var reused = storedToken(true, Instant.now().plus(REFRESH_TTL));
    stubTokenLookup(reused);

    var exception = assertThrows(
      InvalidTokenException.class,
      () -> refreshTokenUseCase.execute(new RefreshTokenCommand(RAW_TOKEN))
    );

    assertEquals("Refresh token reutilizado, faça login novamente", exception.getMessage());
    verify(refreshTokenRepository).revokeAllByUserId(USER_ID);
    verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
    verifyNoInteractions(userRepository, accessTokenIssuer);
  }

  @Test
  void shouldThrowInvalidToken_WhenTokenIsExpired() {
    var expired = storedToken(false, Instant.now().minusSeconds(60));
    stubTokenLookup(expired);

    var exception = assertThrows(
      InvalidTokenException.class,
      () -> refreshTokenUseCase.execute(new RefreshTokenCommand(RAW_TOKEN))
    );

    assertEquals("Refresh token expirado, faça login novamente", exception.getMessage());
    verifyNoInteractions(userRepository, accessTokenIssuer);
    verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
  }

  @Test
  void shouldThrowInvalidToken_WhenUserIsNotFound() {
    var current = storedToken(false, Instant.now().plus(REFRESH_TTL));
    stubTokenLookup(current);
    when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

    var exception = assertThrows(
      InvalidTokenException.class,
      () -> refreshTokenUseCase.execute(new RefreshTokenCommand(RAW_TOKEN))
    );

    assertEquals("Usuário inválido", exception.getMessage());
    verifyNoInteractions(accessTokenIssuer);
    verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
  }

  @Test
  void shouldThrowInvalidToken_WhenUserIsInactive() {
    var current = storedToken(false, Instant.now().plus(REFRESH_TTL));
    stubTokenLookup(current);
    var inactive = activeUser();
    inactive.setActive(false);
    when(userRepository.findById(USER_ID)).thenReturn(Optional.of(inactive));

    assertThrows(
      InvalidTokenException.class,
      () -> refreshTokenUseCase.execute(new RefreshTokenCommand(RAW_TOKEN))
    );
    verifyNoInteractions(accessTokenIssuer);
    verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
  }
}
