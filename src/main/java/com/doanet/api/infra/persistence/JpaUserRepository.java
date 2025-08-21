package com.doanet.api.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
  @Modifying
  @Query("UPDATE UserEntity u SET u.isActive = false WHERE u.id = :id")
  void disableUser(@Param("id") Long id);
}
