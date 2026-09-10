package com.doanet.api.domain.entities.auth;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenTest {

  private static final Instant EXPIRES_AT = Instant.parse("2026-09-17T12:00:00Z");

  @Test
  void shouldCreateToken_WhenAllRequiredFieldsAreProvided() {
    var createdAt = Instant.parse("2026-09-10T12:00:00Z");
    var token = new RefreshToken(1L, 10L, "abc123", EXPIRES_AT, false, createdAt);

    assertEquals(1L, token.getId());
    assertEquals(10L, token.getUserId());
    assertEquals("abc123", token.getTokenHash());
    assertEquals(EXPIRES_AT, token.getExpiresAt());
    assertEquals(createdAt, token.getCreatedAt());
    assertFalse(token.isRevoked());
  }

  @Test
  void shouldDefaultCreatedAtToNow_WhenNotProvided() {
    var before = Instant.now();
    var token = new RefreshToken(null, 10L, "abc123", EXPIRES_AT, false, null);

    assertNotNull(token.getCreatedAt());
    assertFalse(token.getCreatedAt().isBefore(before));
  }

  @Test
  void shouldThrow_WhenUserIdIsNull() {
    var exception = assertThrows(
      IllegalArgumentException.class,
      () -> new RefreshToken(null, null, "abc123", EXPIRES_AT, false, Instant.now())
    );

    assertEquals("Usuário é obrigatório", exception.getMessage());
  }

  @Test
  void shouldThrow_WhenTokenHashIsBlank() {
    var exception = assertThrows(
      IllegalArgumentException.class,
      () -> new RefreshToken(null, 10L, "  ", EXPIRES_AT, false, Instant.now())
    );

    assertEquals("Hash do refresh token é obrigatório", exception.getMessage());
  }

  @Test
  void shouldThrow_WhenExpiresAtIsNull() {
    var exception = assertThrows(
      IllegalArgumentException.class,
      () -> new RefreshToken(null, 10L, "abc123", null, false, Instant.now())
    );

    assertEquals("Data de expiração do refresh token é obrigatória", exception.getMessage());
  }

  @Test
  void shouldReportExpired_WhenExpirationIsInThePast() {
    var token = new RefreshToken(null, 10L, "abc123", Instant.now().minus(1, ChronoUnit.MINUTES), false, Instant.now());

    assertTrue(token.isExpired(Instant.now()));
  }

  @Test
  void shouldReportExpired_WhenExpirationEqualsNow() {
    var now = Instant.now();
    var token = new RefreshToken(null, 10L, "abc123", now, false, now.minusSeconds(60));

    assertTrue(token.isExpired(now));
  }

  @Test
  void shouldReportUsable_WhenNotRevokedAndNotExpired() {
    var token = new RefreshToken(null, 10L, "abc123", Instant.now().plus(1, ChronoUnit.HOURS), false, Instant.now());

    assertTrue(token.isUsable(Instant.now()));
  }

  @Test
  void shouldReportNotUsable_WhenRevoked() {
    var token = new RefreshToken(null, 10L, "abc123", Instant.now().plus(1, ChronoUnit.HOURS), false, Instant.now());
    token.revoke();

    assertTrue(token.isRevoked());
    assertFalse(token.isUsable(Instant.now()));
  }
}
