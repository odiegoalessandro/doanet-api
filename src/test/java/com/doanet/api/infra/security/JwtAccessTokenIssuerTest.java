package com.doanet.api.infra.security;

import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtAccessTokenIssuerTest {

  private static final String SECRET = "test-secret-only-for-tests-never-use-this-value-32b";
  private static final String ISSUER = "doanet-api";

  private JwtAccessTokenIssuer accessTokenIssuer;
  private NimbusJwtDecoder jwtDecoder;

  @BeforeEach
  void setUp() {
    var secretKey = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

    var jwtProperties = new JwtProperties();
    jwtProperties.setSecret(SECRET);
    jwtProperties.setIssuer(ISSUER);
    jwtProperties.setAccessTokenTtl(Duration.ofMinutes(15));

    accessTokenIssuer = new JwtAccessTokenIssuer(
      new NimbusJwtEncoder(new ImmutableSecret<SecurityContext>(secretKey)),
      jwtProperties
    );

    jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey)
      .macAlgorithm(MacAlgorithm.HS256)
      .build();
  }

  private User user() {
    var user = new User(
      null, "João", "joao@email.com", "{bcrypt}hash", "11999999999",
      "Rua A", "123", "Centro", "São Paulo", "SP", "12345-678",
      null, null, UserType.ONG, true
    );
    user.setId(42L);
    return user;
  }

  @Test
  void shouldIssueVerifiableToken_WithUserClaims() {
    var token = accessTokenIssuer.issue(user());
    var decoded = jwtDecoder.decode(token);

    assertEquals(ISSUER, decoded.getClaimAsString("iss"));
    assertEquals("42", decoded.getSubject());
    assertEquals("joao@email.com", decoded.getClaimAsString("email"));
    assertEquals("João", decoded.getClaimAsString("name"));
    assertEquals("ONG", decoded.getClaimAsString("userType"));
    assertEquals(List.of("ONG"), decoded.getClaimAsStringList("roles"));
  }

  @Test
  void shouldSetExpirationAccordingToConfiguredTtl() {
    var decoded = jwtDecoder.decode(accessTokenIssuer.issue(user()));

    var ttlSeconds = Duration.between(decoded.getIssuedAt(), decoded.getExpiresAt()).getSeconds();

    assertEquals(900L, ttlSeconds);
  }

  @Test
  void shouldReportAccessTokenTtlInSeconds() {
    assertEquals(900L, accessTokenIssuer.accessTokenTtlSeconds());
  }
}
