package com.doanet.api.config;

import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.ong.*;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.infra.gateways.OngEntityMapper;
import com.doanet.api.infra.gateways.OngRepositoryImpl;
import com.doanet.api.infra.persistence.JpaOngRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OngConfig {
  @Bean
  public CreateOngUseCase createOngUseCase(
      OngRepository ongRepository,
      GeolocateUserUseCase geolocateUserUseCase,
      PasswordHasher passwordHasher,
      RecordAuditUseCase recordAuditUseCase) {
    return new CreateOngUseCase(
        ongRepository, geolocateUserUseCase, passwordHasher, recordAuditUseCase);
  }

  @Bean
  public FindOngByIdUseCase findOngByIdUseCase(OngRepository ongRepository) {
    return new FindOngByIdUseCase(ongRepository);
  }

  @Bean
  public FindOngByCnpjUseCase findOngByCnpjUseCase(OngRepository ongRepository) {
    return new FindOngByCnpjUseCase(ongRepository);
  }

  @Bean
  public FindOngUseCase findAllActiveOngUseCase(OngRepository ongRepository) {
    return new FindOngUseCase(ongRepository);
  }

  @Bean
  public UpdateOngUseCase updateOngUseCase(
      FindOngByIdUseCase findOngByIdUseCase, OngRepository ongRepository) {
    return new UpdateOngUseCase(findOngByIdUseCase, ongRepository);
  }

  @Bean
  public DisableOngByIdUseCase deleteOngByIdUseCase(
      UserRepository userRepository,
      FindOngByIdUseCase findOngByIdUseCase,
      RecordAuditUseCase recordAuditUseCase) {
    return new DisableOngByIdUseCase(userRepository, findOngByIdUseCase, recordAuditUseCase);
  }

  @Bean
  public OngRepository ongRepositoryImpl(
      JpaOngRepository jpaOngRepository, OngEntityMapper ongEntityMapper) {
    return new OngRepositoryImpl(jpaOngRepository, ongEntityMapper);
  }
}
