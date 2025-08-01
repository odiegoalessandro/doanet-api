package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.infra.persistence.JpaOngRepository;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class OngRepositoryImpl implements OngRepository {
  private final JpaOngRepository jpaOngRepository;
  private final OngEntityMapper mapper;


  public OngRepositoryImpl(JpaOngRepository jpaOngRepository, OngEntityMapper mapper) {
    this.jpaOngRepository = jpaOngRepository;
    this.mapper = mapper;
  }

  @Override
  public Ong save(Ong ong) {
    var ongEntity = this.mapper.toEntity(ong);
    var saved = this.jpaOngRepository.save(ongEntity);

    return this.mapper.toDomain(saved);
  }

  @Override
  public Optional<Ong> findById(Long id, boolean isActive) {
    return this.jpaOngRepository.findById(id, isActive).map(mapper::toDomain);
  }

  @Override
  public Optional<Ong> findByCnpj(String cnpj, boolean isActive) {
    return this.jpaOngRepository.findByCnpj(cnpj, isActive).map(mapper::toDomain);
  }

  @Override
  public PageResponse<Ong> findAll(Pagination pagination, boolean isActive) {
    var pageNumber = Math.max(1, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaOngRepository.findAll(pageable, isActive);
    var dtoPage = jpaPage.map(mapper::toDomain);

    return new PageResponse<>(
      dtoPage.getContent(),
      dtoPage.getTotalElements(),
      dtoPage.getTotalPages(),
      dtoPage.getNumber() + 1
    );
  }

  @Override
  public void deleteById(Long id){
    this.jpaOngRepository.deleteById(id);
  }
}
