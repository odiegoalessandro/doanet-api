package com.doanet.api.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
  Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);

  @Modifying
  @Query("UPDATE RefreshTokenEntity r SET r.revoked = true WHERE r.userId = :userId AND r.revoked = false")
  void revokeAllByUserId(@Param("userId") Long userId);

  @Modifying
  @Query("DELETE FROM RefreshTokenEntity r WHERE r.expiresAt < :now OR r.revoked = true")
  int deleteExpiredOrRevoked(@Param("now") Instant now);
}
