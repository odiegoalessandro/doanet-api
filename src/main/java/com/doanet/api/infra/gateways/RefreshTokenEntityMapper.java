package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.auth.RefreshToken;
import com.doanet.api.infra.persistence.RefreshTokenEntity;

public class RefreshTokenEntityMapper {
  public RefreshTokenEntity toEntity(RefreshToken refreshToken) {
    return new RefreshTokenEntity(
      refreshToken.getId(),
      refreshToken.getUserId(),
      refreshToken.getTokenHash(),
      refreshToken.getExpiresAt(),
      refreshToken.isRevoked(),
      refreshToken.getCreatedAt()
    );
  }

  public RefreshToken toDomain(RefreshTokenEntity entity) {
    return new RefreshToken(
      entity.getId(),
      entity.getUserId(),
      entity.getTokenHash(),
      entity.getExpiresAt(),
      entity.isRevoked(),
      entity.getCreatedAt()
    );
  }
}
