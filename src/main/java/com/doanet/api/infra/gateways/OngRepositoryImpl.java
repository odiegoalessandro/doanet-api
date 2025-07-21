package com.doanet.api.infra.gateways;

import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.infra.persistence.JpaOngRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
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
  public Optional<Ong> findByCnpj(String cnpj) {
    return this.jpaOngRepository.findByCnpjActive(cnpj).map(mapper::toDomain);
  }

  @Override
  public void deleteById(Long id){
    this.jpaOngRepository.deleteById(id);
  }
}
