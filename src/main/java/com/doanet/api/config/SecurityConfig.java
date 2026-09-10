package com.doanet.api.config;

import com.doanet.api.infra.security.JwtProperties;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.SecurityContext;
import com.doanet.api.infra.security.RestAccessDeniedHandler;
import com.doanet.api.infra.security.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private static final int MIN_SECRET_LENGTH_BYTES = 32;
  private static final String HMAC_ALGORITHM = "HmacSHA256";

  private static final String[] PUBLIC_ROUTES = {
    "/auth/**",
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-ui.html"
  };

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public JwtEncoder jwtEncoder(JwtProperties jwtProperties) {
    var jwkSource = new ImmutableSecret<SecurityContext>(secretKey(jwtProperties));
    return new NimbusJwtEncoder(jwkSource);
  }

  @Bean
  public JwtDecoder jwtDecoder(JwtProperties jwtProperties) {
    return NimbusJwtDecoder.withSecretKey(secretKey(jwtProperties))
      .macAlgorithm(MacAlgorithm.HS256)
      .build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                 RestAuthenticationEntryPoint authenticationEntryPoint,
                                                 RestAccessDeniedHandler accessDeniedHandler) throws Exception {
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(PUBLIC_ROUTES).permitAll()
        .anyRequest().authenticated()
      )
      .oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
        .authenticationEntryPoint(authenticationEntryPoint)
      )
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint(authenticationEntryPoint)
        .accessDeniedHandler(accessDeniedHandler)
      )
      .build();
  }

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    var converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(jwt -> {
      List<String> roles = jwt.getClaimAsStringList("roles");
      if (roles == null) {
        return List.of();
      }
      return roles.stream()
        .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role))
        .toList();
    });
    return converter;
  }

  private SecretKey secretKey(JwtProperties jwtProperties) {
    var secret = jwtProperties.getSecret();

    if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < MIN_SECRET_LENGTH_BYTES) {
      throw new IllegalStateException(
        "security.jwt.secret deve ser configurado com no mínimo " + MIN_SECRET_LENGTH_BYTES + " bytes"
      );
    }

    return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
  }
}
