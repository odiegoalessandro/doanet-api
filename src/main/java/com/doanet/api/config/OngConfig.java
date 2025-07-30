package com.doanet.api.config;

import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.application.usecases.ong.*;
import com.doanet.api.application.usecases.user.CreateUserUseCase;
import com.doanet.api.infra.gateways.OngEntityMapper;
import com.doanet.api.infra.gateways.OngRepositoryImpl;
import com.doanet.api.infra.persistence.JpaOngRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OngConfig {
  @Bean
  public CreateOngUseCase createOngUseCase(OngRepository ongRepository, CreateUserUseCase createUserUseCase){
    return new CreateOngUseCase(ongRepository);
  }

  @Bean
  public FindActiveOngByIdUseCase findActiveOngByIdUseCase(OngRepository ongRepository){
    return new FindActiveOngByIdUseCase(ongRepository);
  }

  @Bean
  public FindActiveOngByCnpjUseCase findActiveOngByCnpjUseCase(OngRepository ongRepository){
    return new FindActiveOngByCnpjUseCase(ongRepository);
  }

  @Bean
  public FindOngByCnpjUseCase findOngByCnpjUseCase(OngRepository ongRepository){
    return new FindOngByCnpjUseCase(ongRepository);
  }

  @Bean
  public FindAllActiveOngUseCase findAllActiveOngUseCase(OngRepository ongRepository) {
    return new FindAllActiveOngUseCase(ongRepository);
  }

  @Bean
  public UpdateOngUseCase updateOngUseCase(
    FindActiveOngByIdUseCase findActiveOngByIdUseCase,
    OngRepository ongRepository
  ) {
    return new UpdateOngUseCase(findActiveOngByIdUseCase, ongRepository);
  }

  @Bean
  public DeleteOngByIdUseCase deleteOngByIdUseCase(
    OngRepository ongRepository
  ) {
    return new DeleteOngByIdUseCase(ongRepository);
  }

  @Bean
  public OngRepository ongRepositoryImpl(JpaOngRepository jpaOngRepository, OngEntityMapper ongEntityMapper) {
    return new OngRepositoryImpl(jpaOngRepository, ongEntityMapper);
  }
}
