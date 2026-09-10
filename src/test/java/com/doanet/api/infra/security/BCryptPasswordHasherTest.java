package com.doanet.api.infra.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import static org.junit.jupiter.api.Assertions.*;

class BCryptPasswordHasherTest {

  private BCryptPasswordHasher passwordHasher;

  @BeforeEach
  void setUp() {
    passwordHasher = new BCryptPasswordHasher(PasswordEncoderFactories.createDelegatingPasswordEncoder());
  }

  @Test
  void shouldHashWithBcryptPrefix() {
    var hash = passwordHasher.hash("senha123");

    assertTrue(hash.startsWith("{bcrypt}"));
    assertNotEquals("senha123", hash);
  }

  @Test
  void shouldProduceDifferentHashes_ForTheSamePassword() {
    var first = passwordHasher.hash("senha123");
    var second = passwordHasher.hash("senha123");

    assertNotEquals(first, second);
  }

  @Test
  void shouldMatchRawPassword_AgainstItsHash() {
    var hash = passwordHasher.hash("senha123");

    assertTrue(passwordHasher.matches("senha123", hash));
  }

  @Test
  void shouldNotMatchWrongPassword() {
    var hash = passwordHasher.hash("senha123");

    assertFalse(passwordHasher.matches("outra-senha", hash));
  }

  @Test
  void shouldReportHashed_WhenPasswordHasEncoderPrefix() {
    assertTrue(passwordHasher.isHashed("{bcrypt}$2a$10$abcdefghijklmnopqrstuv"));
  }

  @Test
  void shouldReportNotHashed_ForLegacyPlaintextPassword() {
    assertFalse(passwordHasher.isHashed("senha123"));
    assertFalse(passwordHasher.isHashed(null));
  }

  @Test
  void shouldMatchLegacyPlaintextPassword_ByExactComparison() {
    assertTrue(passwordHasher.matches("senha123", "senha123"));
    assertFalse(passwordHasher.matches("senha123", "senha124"));
  }

  @Test
  void shouldNotMatch_WhenEitherArgumentIsNull() {
    assertFalse(passwordHasher.matches(null, "{bcrypt}hash"));
    assertFalse(passwordHasher.matches("senha123", null));
    assertFalse(passwordHasher.matches(null, null));
  }
}
