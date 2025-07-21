package com.doanet.api.application.gateways;

import com.doanet.api.domain.entities.ong.Ong;

import java.util.Optional;

public interface OngRepository {
  Ong save(Ong ong);
  Optional<Ong> findByIdActive(Long id);
  Optional<Ong> findByCnpjActive(String cnpj);
//  PageResult<Ong> findAllActive(Pagination pageable);
  Optional<Ong> findByCnpj(String cnpj);
  void deleteById(Long id);
}
