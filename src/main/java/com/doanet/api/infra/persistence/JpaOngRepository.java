package com.doanet.api.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaOngRepository extends JpaRepository<OngEntity, Long> {

  @Query("SELECT o FROM OngEntity o WHERE id = :id AND o.user.isActive = :isActive")
  Optional<OngEntity> findById(@Param("id") Long id, @Param("isActive") boolean isActive);

  @Query("SELECT o FROM OngEntity o WHERE o.cnpj = :cnpj AND o.user.isActive = :isActive")
  Optional<OngEntity> findByCnpj(@Param("cnpj") String cnpj, @Param("isActive") boolean isActive);

  @Query("SELECT o FROM OngEntity o WHERE o.user.isActive = :isActive")
  Page<OngEntity> findAll(Pageable pageable, @Param("isActive") boolean isActive);
}
