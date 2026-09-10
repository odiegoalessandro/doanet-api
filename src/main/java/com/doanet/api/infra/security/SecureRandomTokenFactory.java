package com.doanet.api.infra.security;

import com.doanet.api.application.gateways.TokenFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

public class SecureRandomTokenFactory implements TokenFactory {
  private static final int TOKEN_BYTES = 32;
  private static final String HASH_ALGORITHM = "SHA-256";

  private final SecureRandom secureRandom = new SecureRandom();

  @Override
  public String generate() {
    var bytes = new byte[TOKEN_BYTES];
    this.secureRandom.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }

  @Override
  public String hash(String rawToken) {
    if (rawToken == null) {
      throw new IllegalArgumentException("Token é obrigatório");
    }

    try {
      var digest = MessageDigest.getInstance(HASH_ALGORITHM)
        .digest(rawToken.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(digest);
    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalStateException("Algoritmo de hash indisponível: " + HASH_ALGORITHM, ex);
    }
  }
}
