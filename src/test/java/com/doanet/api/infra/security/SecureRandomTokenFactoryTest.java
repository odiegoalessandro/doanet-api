package com.doanet.api.infra.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecureRandomTokenFactoryTest {

  private SecureRandomTokenFactory tokenFactory;

  @BeforeEach
  void setUp() {
    tokenFactory = new SecureRandomTokenFactory();
  }

  @Test
  void shouldGenerateUrlSafeToken_With256BitsOfEntropy() {
    var token = tokenFactory.generate();

    assertNotNull(token);
    assertEquals(43, token.length());
    assertTrue(token.matches("[A-Za-z0-9_-]+"));
  }

  @Test
  void shouldGenerateDifferentTokens_OnEachCall() {
    assertNotEquals(tokenFactory.generate(), tokenFactory.generate());
  }

  @Test
  void shouldHashTokenAsDeterministicSha256Hex() {
    var first = tokenFactory.hash("raw-token");
    var second = tokenFactory.hash("raw-token");

    assertEquals(first, second);
    assertEquals(64, first.length());
    assertTrue(first.matches("[0-9a-f]{64}"));
  }

  @Test
  void shouldProduceDifferentHashes_ForDifferentTokens() {
    assertNotEquals(tokenFactory.hash("token-a"), tokenFactory.hash("token-b"));
  }

  @Test
  void shouldThrow_WhenHashingNullToken() {
    var exception = assertThrows(IllegalArgumentException.class, () -> tokenFactory.hash(null));

    assertEquals("Token é obrigatório", exception.getMessage());
  }
}
