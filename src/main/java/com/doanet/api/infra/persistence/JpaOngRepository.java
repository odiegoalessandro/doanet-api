package com.doanet.api.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaOngRepository extends JpaRepository<OngEntity, Long> {

  @Query("SELECT o FROM Ong o WHERE id = :id AND o.user.isActive = true")
  Optional<OngEntity> findByIdActive(@Param("id") Long id);

  @Query("SELECT o FROM Ong o WHERE o.cnpj = :cnpj AND o.user.isActive = true")
  Optional<OngEntity> findByCnpjActive(@Param("cnpj") String cnpj);

  @Query("SELECT o FROM Ong o WHERE o.user.isActive = true")
  Page<OngEntity> findAllActive(Pageable pageable);

  Optional<OngEntity> findByCnpj(String cnpj);
}
