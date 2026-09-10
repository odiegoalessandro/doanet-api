package com.doanet.api.infra.security;

import com.doanet.api.application.gateways.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class BCryptPasswordHasher implements PasswordHasher {
  private final PasswordEncoder passwordEncoder;

  public BCryptPasswordHasher(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String hash(String rawPassword) {
    return this.passwordEncoder.encode(rawPassword);
  }

  @Override
  public boolean matches(String rawPassword, String storedPassword) {
    if (rawPassword == null || storedPassword == null) {
      return false;
    }

    if (!isHashed(storedPassword)) {
      return MessageDigest.isEqual(
        rawPassword.getBytes(StandardCharsets.UTF_8),
        storedPassword.getBytes(StandardCharsets.UTF_8)
      );
    }

    return this.passwordEncoder.matches(rawPassword, storedPassword);
  }

  @Override
  public boolean isHashed(String storedPassword) {
    return storedPassword != null && storedPassword.startsWith("{");
  }
}
