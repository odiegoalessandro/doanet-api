package com.doanet.api.application.gateways;

public interface PasswordHasher {
  String hash(String rawPassword);

  boolean matches(String rawPassword, String storedPassword);

  boolean isHashed(String storedPassword);
}
