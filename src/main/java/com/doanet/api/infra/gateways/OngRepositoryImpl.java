package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.infra.persistence.JpaOngRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

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
  public Optional<Ong> findByIdActive(Long id) {
    return this.jpaOngRepository.findByIdActive(id).map(mapper::toDomain);
  }

  @Override
  public Optional<Ong> findByCnpjActive(String cnpj) {
    return this.jpaOngRepository.findByCnpjActive(cnpj).map(mapper::toDomain);
  }

  @Override
  public PageResponse<Ong> findAllActive(Pagination pagination) {
    var pageNumber = Math.max(1, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.jpaOngRepository.findAllActive(pageable);
    var dtoPage = jpaPage.map(mapper::toDomain);

    return new PageResponse<>(
      dtoPage.getContent(),
      dtoPage.getTotalElements(),
      dtoPage.getTotalPages(),
      dtoPage.getNumber() + 1
    );
  }

  @Override
  public Optional<Ong> findByCnpj(String cnpj) {
    return this.jpaOngRepository.findByCnpjActive(cnpj).map(mapper::toDomain);
  }

  @Override
  public void deleteById(Long id){
    this.jpaOngRepository.deleteById(id);
  }
}
