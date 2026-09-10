package com.doanet.api.infra.gateways;

import com.doanet.api.application.gateways.RefreshTokenRepository;
import com.doanet.api.domain.entities.auth.RefreshToken;
import com.doanet.api.infra.persistence.JpaRefreshTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
  private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
  private final RefreshTokenEntityMapper mapper;

  public RefreshTokenRepositoryImpl(JpaRefreshTokenRepository jpaRefreshTokenRepository,
                                    RefreshTokenEntityMapper mapper) {
    this.jpaRefreshTokenRepository = jpaRefreshTokenRepository;
    this.mapper = mapper;
  }

  @Override
  @Transactional
  public RefreshToken save(RefreshToken refreshToken) {
    var saved = this.jpaRefreshTokenRepository.save(this.mapper.toEntity(refreshToken));
    return this.mapper.toDomain(saved);
  }

  @Override
  public Optional<RefreshToken> findByTokenHash(String tokenHash) {
    return this.jpaRefreshTokenRepository.findByTokenHash(tokenHash)
      .map(this.mapper::toDomain);
  }

  @Override
  @Transactional
  public void revokeAllByUserId(Long userId) {
    this.jpaRefreshTokenRepository.revokeAllByUserId(userId);
  }

  @Override
  @Transactional
  public int deleteExpired(Instant now) {
    return this.jpaRefreshTokenRepository.deleteExpiredOrRevoked(now);
  }
}
