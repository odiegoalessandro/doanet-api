package com.doanet.api.infra.security;

import com.doanet.api.application.gateways.AccessTokenIssuer;
import com.doanet.api.domain.entities.user.User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.List;

public class JwtAccessTokenIssuer implements AccessTokenIssuer {
  private final JwtEncoder jwtEncoder;
  private final JwtProperties jwtProperties;

  public JwtAccessTokenIssuer(JwtEncoder jwtEncoder, JwtProperties jwtProperties) {
    this.jwtEncoder = jwtEncoder;
    this.jwtProperties = jwtProperties;
  }

  @Override
  public String issue(User user) {
    var now = Instant.now();
    var roles = List.of(user.getUserType().name());

    var claims = JwtClaimsSet.builder()
      .issuer(this.jwtProperties.getIssuer())
      .issuedAt(now)
      .expiresAt(now.plus(this.jwtProperties.getAccessTokenTtl()))
      .subject(String.valueOf(user.getId()))
      .claim("email", user.getEmail())
      .claim("name", user.getName())
      .claim("userType", user.getUserType().name())
      .claim("roles", roles)
      .build();

    var header = JwsHeader.with(MacAlgorithm.HS256).build();

    return this.jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
  }

  @Override
  public long accessTokenTtlSeconds() {
    return this.jwtProperties.getAccessTokenTtl().toSeconds();
  }
}
