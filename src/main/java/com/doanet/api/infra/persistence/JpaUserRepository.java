package com.doanet.api.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
  @Modifying
  @Query("UPDATE UserEntity u SET u.isActive = false WHERE u.id = :id")
  void disableUser(@Param("id") Long id);

  @Query("SELECT u FROM UserEntity u WHERE u.isActive = true AND (u.latitude IS NULL OR u.longitude IS NULL)")
  List<UserEntity> findAllWithoutCoordinates();
}
