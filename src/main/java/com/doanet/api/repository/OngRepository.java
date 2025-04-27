package com.doanet.api.repository;

import com.doanet.api.entity.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OngRepository extends JpaRepository<Ong, Long> {
  Optional<Ong> findByCnpj(String cnpj);
}
