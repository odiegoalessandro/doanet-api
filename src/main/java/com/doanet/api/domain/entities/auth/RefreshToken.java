package com.doanet.api.domain.entities.auth;

import java.time.Instant;

public class RefreshToken {
  private final Long id;
  private final Long userId;
  private final String tokenHash;
  private final Instant expiresAt;
  private final Instant createdAt;
  private boolean revoked;

  public RefreshToken(Long id,
                      Long userId,
                      String tokenHash,
                      Instant expiresAt,
                      boolean revoked,
                      Instant createdAt) {
    if (userId == null)
      throw new IllegalArgumentException("Usuário é obrigatório");
    if (tokenHash == null || tokenHash.isBlank())
      throw new IllegalArgumentException("Hash do refresh token é obrigatório");
    if (expiresAt == null)
      throw new IllegalArgumentException("Data de expiração do refresh token é obrigatória");

    this.id = id;
    this.userId = userId;
    this.tokenHash = tokenHash;
    this.expiresAt = expiresAt;
    this.revoked = revoked;
    this.createdAt = createdAt != null ? createdAt : Instant.now();
  }

  public boolean isExpired(Instant now) {
    return !this.expiresAt.isAfter(now);
  }

  public boolean isUsable(Instant now) {
    return !this.revoked && !isExpired(now);
  }

  public void revoke() {
    this.revoked = true;
  }

  public Long getId() { return id; }
  public Long getUserId() { return userId; }
  public String getTokenHash() { return tokenHash; }
  public Instant getExpiresAt() { return expiresAt; }
  public boolean isRevoked() { return revoked; }
  public Instant getCreatedAt() { return createdAt; }
}
