package com.doanet.api.config;

import com.doanet.api.application.gateways.AccessTokenIssuer;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.gateways.RefreshTokenRepository;
import com.doanet.api.application.gateways.TokenFactory;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.auth.LoginUseCase;
import com.doanet.api.application.usecases.auth.LogoutUseCase;
import com.doanet.api.application.usecases.auth.PurgeExpiredRefreshTokensUseCase;
import com.doanet.api.application.usecases.auth.RefreshTokenUseCase;
import com.doanet.api.infra.gateways.RefreshTokenEntityMapper;
import com.doanet.api.infra.gateways.RefreshTokenRepositoryImpl;
import com.doanet.api.infra.persistence.JpaRefreshTokenRepository;
import com.doanet.api.infra.security.BCryptPasswordHasher;
import com.doanet.api.infra.security.JwtAccessTokenIssuer;
import com.doanet.api.infra.security.JwtProperties;
import com.doanet.api.infra.security.SecureRandomTokenFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@Configuration
public class AuthConfig {
  @Bean
  public PasswordHasher passwordHasher(PasswordEncoder passwordEncoder) {
    return new BCryptPasswordHasher(passwordEncoder);
  }

  @Bean
  public TokenFactory tokenFactory() {
    return new SecureRandomTokenFactory();
  }

  @Bean
  public RefreshTokenEntityMapper refreshTokenEntityMapper() {
    return new RefreshTokenEntityMapper();
  }

  @Bean
  public RefreshTokenRepository refreshTokenRepository(JpaRefreshTokenRepository jpaRefreshTokenRepository,
                                                        RefreshTokenEntityMapper refreshTokenEntityMapper) {
    return new RefreshTokenRepositoryImpl(jpaRefreshTokenRepository, refreshTokenEntityMapper);
  }

  @Bean
  public AccessTokenIssuer accessTokenIssuer(JwtEncoder jwtEncoder, JwtProperties jwtProperties) {
    return new JwtAccessTokenIssuer(jwtEncoder, jwtProperties);
  }

  @Bean
  public LoginUseCase loginUseCase(UserRepository userRepository,
                                   PasswordHasher passwordHasher,
                                   AccessTokenIssuer accessTokenIssuer,
                                   RefreshTokenRepository refreshTokenRepository,
                                   TokenFactory tokenFactory,
                                   JwtProperties jwtProperties) {
    return new LoginUseCase(
      userRepository,
      passwordHasher,
      accessTokenIssuer,
      refreshTokenRepository,
      tokenFactory,
      jwtProperties.getRefreshTokenTtl()
    );
  }

  @Bean
  public RefreshTokenUseCase refreshTokenUseCase(RefreshTokenRepository refreshTokenRepository,
                                                 UserRepository userRepository,
                                                 AccessTokenIssuer accessTokenIssuer,
                                                 TokenFactory tokenFactory,
                                                 JwtProperties jwtProperties) {
    return new RefreshTokenUseCase(
      refreshTokenRepository,
      userRepository,
      accessTokenIssuer,
      tokenFactory,
      jwtProperties.getRefreshTokenTtl()
    );
  }

  @Bean
  public LogoutUseCase logoutUseCase(RefreshTokenRepository refreshTokenRepository, TokenFactory tokenFactory) {
    return new LogoutUseCase(refreshTokenRepository, tokenFactory);
  }

  @Bean
  public PurgeExpiredRefreshTokensUseCase purgeExpiredRefreshTokensUseCase(RefreshTokenRepository refreshTokenRepository) {
    return new PurgeExpiredRefreshTokensUseCase(refreshTokenRepository);
  }
}
