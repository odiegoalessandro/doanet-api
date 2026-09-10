package com.doanet.api.infra.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
  private String secret;
  private String issuer = "doanet-api";
  private Duration accessTokenTtl = Duration.ofMinutes(15);
  private Duration refreshTokenTtl = Duration.ofDays(7);

  public String getSecret() { return secret; }
  public void setSecret(String secret) { this.secret = secret; }

  public String getIssuer() { return issuer; }
  public void setIssuer(String issuer) { this.issuer = issuer; }

  public Duration getAccessTokenTtl() { return accessTokenTtl; }
  public void setAccessTokenTtl(Duration accessTokenTtl) { this.accessTokenTtl = accessTokenTtl; }

  public Duration getRefreshTokenTtl() { return refreshTokenTtl; }
  public void setRefreshTokenTtl(Duration refreshTokenTtl) { this.refreshTokenTtl = refreshTokenTtl; }
}
