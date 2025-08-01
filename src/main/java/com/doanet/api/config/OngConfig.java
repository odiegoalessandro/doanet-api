package com.doanet.api.config;

import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.application.gateways.UserRepository;
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
  public FindOngByIdUseCase findOngByIdUseCase(OngRepository ongRepository){
    return new FindOngByIdUseCase(ongRepository);
  }

  @Bean
  public FindOngByCnpjUseCase findOngByCnpjUseCase(OngRepository ongRepository){
    return new FindOngByCnpjUseCase(ongRepository);
  }

  @Bean
  public FindOngUseCase findAllActiveOngUseCase(OngRepository ongRepository) {
    return new FindOngUseCase(ongRepository);
  }

  @Bean
  public UpdateOngUseCase updateOngUseCase(
    FindOngByIdUseCase findOngByIdUseCase,
    OngRepository ongRepository
  ) {
    return new UpdateOngUseCase(findOngByIdUseCase, ongRepository);
  }

  @Bean
  public DisableOngByIdUseCase deleteOngByIdUseCase(
    UserRepository userRepository,
    FindOngByIdUseCase findOngByIdUseCase
  ) {
    return new DisableOngByIdUseCase(userRepository, findOngByIdUseCase);
  }

  @Bean
  public OngRepository ongRepositoryImpl(JpaOngRepository jpaOngRepository, OngEntityMapper ongEntityMapper) {
    return new OngRepositoryImpl(jpaOngRepository, ongEntityMapper);
  }
}
