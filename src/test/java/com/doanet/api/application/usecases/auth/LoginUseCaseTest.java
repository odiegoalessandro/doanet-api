package com.doanet.api.application.usecases.auth;

import com.doanet.api.application.commands.LoginCommand;
import com.doanet.api.application.exceptions.InvalidCredentialsException;
import com.doanet.api.application.gateways.AccessTokenIssuer;
import com.doanet.api.application.gateways.PasswordHasher;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginUseCaseTest {

  private static final Long USER_ID = 1L;
  private static final Duration REFRESH_TTL = Duration.ofDays(7);

  private UserRepository userRepository;
  private PasswordHasher passwordHasher;
  private AccessTokenIssuer accessTokenIssuer;
  private RefreshTokenRepository refreshTokenRepository;
  private TokenFactory tokenFactory;
  private LoginUseCase loginUseCase;

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    passwordHasher = mock(PasswordHasher.class);
    accessTokenIssuer = mock(AccessTokenIssuer.class);
    refreshTokenRepository = mock(RefreshTokenRepository.class);
    tokenFactory = mock(TokenFactory.class);
    loginUseCase = new LoginUseCase(
      userRepository,
      passwordHasher,
      accessTokenIssuer,
      refreshTokenRepository,
      tokenFactory,
      REFRESH_TTL
    );
  }

  private User activeUser(String storedPassword) {
    var user = new User(
      null,
      "João",
      "joao@email.com",
      storedPassword,
      "11999999999",
      "Rua A",
      "123",
      "Centro",
      "São Paulo",
      "SP",
      "12345-678",
      null,
      null,
      UserType.DONOR,
      true
    );
    user.setId(USER_ID);
    return user;
  }

  private void stubValidCredentials() {
    when(userRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(activeUser("{bcrypt}hash")));
    when(passwordHasher.matches("senha123", "{bcrypt}hash")).thenReturn(true);
    when(passwordHasher.isHashed("{bcrypt}hash")).thenReturn(true);
    when(tokenFactory.generate()).thenReturn("raw-refresh");
    when(tokenFactory.hash("raw-refresh")).thenReturn("hashed-refresh");
    when(accessTokenIssuer.issue(any(User.class))).thenReturn("access-token");
    when(accessTokenIssuer.accessTokenTtlSeconds()).thenReturn(900L);
  }

  @Test
  void shouldIssueAccessAndRefreshTokens_WhenCredentialsAreValid() {
    stubValidCredentials();

    var tokens = loginUseCase.execute(new LoginCommand("joao@email.com", "senha123"));

    assertEquals("access-token", tokens.accessToken());
    assertEquals("raw-refresh", tokens.refreshToken());
    assertEquals("Bearer", tokens.tokenType());
    assertEquals(900L, tokens.expiresIn());
  }

  @Test
  void shouldPersistOnlyTheHashOfTheRefreshToken() {
    stubValidCredentials();

    loginUseCase.execute(new LoginCommand("joao@email.com", "senha123"));

    var captor = ArgumentCaptor.forClass(RefreshToken.class);
    verify(refreshTokenRepository).save(captor.capture());
    var saved = captor.getValue();

    assertEquals("hashed-refresh", saved.getTokenHash());
    assertEquals(USER_ID, saved.getUserId());
    assertFalse(saved.isRevoked());
    assertTrue(saved.getExpiresAt().isAfter(java.time.Instant.now()));
  }

  @Test
  void shouldNormalizeEmailBeforeLookingUpTheUser() {
    stubValidCredentials();

    loginUseCase.execute(new LoginCommand("  Joao@Email.COM  ", "senha123"));

    verify(userRepository).findByEmail("joao@email.com");
  }

  @Test
  void shouldThrowInvalidCredentials_WhenUserDoesNotExist() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    var exception = assertThrows(
      InvalidCredentialsException.class,
      () -> loginUseCase.execute(new LoginCommand("joao@email.com", "senha123"))
    );

    assertEquals("Email ou senha inválidos", exception.getMessage());
    verifyNoInteractions(accessTokenIssuer, tokenFactory, refreshTokenRepository);
  }

  @Test
  void shouldThrowInvalidCredentials_WhenUserIsInactive() {
    var inactive = activeUser("{bcrypt}hash");
    inactive.setActive(false);
    when(userRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(inactive));

    assertThrows(
      InvalidCredentialsException.class,
      () -> loginUseCase.execute(new LoginCommand("joao@email.com", "senha123"))
    );
    verifyNoInteractions(accessTokenIssuer, tokenFactory, refreshTokenRepository);
  }

  @Test
  void shouldThrowInvalidCredentials_WhenPasswordDoesNotMatch() {
    when(userRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(activeUser("{bcrypt}hash")));
    when(passwordHasher.matches("errada123", "{bcrypt}hash")).thenReturn(false);

    assertThrows(
      InvalidCredentialsException.class,
      () -> loginUseCase.execute(new LoginCommand("joao@email.com", "errada123"))
    );
    verifyNoInteractions(accessTokenIssuer, tokenFactory, refreshTokenRepository);
  }

  @Test
  void shouldUpgradeLegacyPlaintextPassword_OnSuccessfulLogin() {
    var legacy = activeUser("senha123");
    when(userRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(legacy));
    when(passwordHasher.matches("senha123", "senha123")).thenReturn(true);
    when(passwordHasher.isHashed("senha123")).thenReturn(false);
    when(passwordHasher.hash("senha123")).thenReturn("{bcrypt}new-hash");
    when(tokenFactory.generate()).thenReturn("raw-refresh");
    when(tokenFactory.hash("raw-refresh")).thenReturn("hashed-refresh");
    when(accessTokenIssuer.issue(any(User.class))).thenReturn("access-token");
    when(accessTokenIssuer.accessTokenTtlSeconds()).thenReturn(900L);

    loginUseCase.execute(new LoginCommand("joao@email.com", "senha123"));

    assertEquals("{bcrypt}new-hash", legacy.getPassword());
    verify(userRepository).save(legacy);
  }

  @Test
  void shouldNotUpgradePassword_WhenItIsAlreadyHashed() {
    stubValidCredentials();

    loginUseCase.execute(new LoginCommand("joao@email.com", "senha123"));

    verify(userRepository, never()).save(any(User.class));
    verify(userRepository, never()).disableUser(anyLong());
  }
}
