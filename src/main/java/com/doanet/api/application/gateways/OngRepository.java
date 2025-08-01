package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.domain.entities.ong.Ong;

import java.util.Optional;

public interface OngRepository {
  Ong save(Ong ong);
  Optional<Ong> findById(Long id, boolean isActive);
  Optional<Ong> findByCnpj(String cnpj, boolean isActive);
  PageResponse<Ong> findAll(Pagination pagination, boolean isActive);
  void deleteById(Long id);
}
