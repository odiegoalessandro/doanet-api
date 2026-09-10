package com.doanet.api.application.dto;

public record AuthTokens(
  String accessToken,
  String refreshToken,
  String tokenType,
  long expiresIn
) {
  public AuthTokens(String accessToken, String refreshToken, long expiresIn) {
    this(accessToken, refreshToken, "Bearer", expiresIn);
  }
}
