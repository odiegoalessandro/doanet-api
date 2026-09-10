package com.doanet.api.application.usecases.auth;

import com.doanet.api.application.gateways.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PurgeExpiredRefreshTokensUseCaseTest {

  private RefreshTokenRepository refreshTokenRepository;
  private PurgeExpiredRefreshTokensUseCase purgeUseCase;

  @BeforeEach
  void setUp() {
    refreshTokenRepository = mock(RefreshTokenRepository.class);
    purgeUseCase = new PurgeExpiredRefreshTokensUseCase(refreshTokenRepository);
  }

  @Test
  void shouldReturnNumberOfDeletedTokens() {
    when(refreshTokenRepository.deleteExpired(any(Instant.class))).thenReturn(5);

    assertEquals(5, purgeUseCase.execute());
  }

  @Test
  void shouldDeleteUsingCurrentInstant() {
    when(refreshTokenRepository.deleteExpired(any(Instant.class))).thenReturn(0);
    var before = Instant.now();

    purgeUseCase.execute();

    var captor = ArgumentCaptor.forClass(Instant.class);
    verify(refreshTokenRepository).deleteExpired(captor.capture());

    var after = Instant.now();
    var usedInstant = captor.getValue();
    assertFalse(usedInstant.isBefore(before));
    assertFalse(usedInstant.isAfter(after));
  }
}
