package com.doanet.api.config;

import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.donor.*;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.infra.gateways.DonorEntityMappper;
import com.doanet.api.infra.gateways.DonorRepositoryImpl;
import com.doanet.api.infra.persistence.JpaDonorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DonorConfig {
  @Bean
  public CreateDonorUseCase createDonorUseCase(
      DonorRepository donorRepository,
      GeolocateUserUseCase geolocateUserUseCase,
      PasswordHasher passwordHasher,
      RecordAuditUseCase recordAuditUseCase) {
    return new CreateDonorUseCase(
        donorRepository, geolocateUserUseCase, passwordHasher, recordAuditUseCase);
  }

  @Bean
  public FindDonorByIdUseCase findActiveDonorByIdUseCase(DonorRepository donorRepository) {
    return new FindDonorByIdUseCase(donorRepository);
  }

  @Bean
  public FindDonorByDocumentUseCase findDonorByDocumentUseCase(DonorRepository donorRepository) {
    return new FindDonorByDocumentUseCase(donorRepository);
  }

  @Bean
  public FindDonorByReasonSocialUseCase findActiveDonorByReasonSocialUseCase(
      DonorRepository donorRepository) {
    return new FindDonorByReasonSocialUseCase(donorRepository);
  }

  @Bean
  public FindAllDonorsUseCase findAllActiveDonorsUseCase(DonorRepository donorRepository) {
    return new FindAllDonorsUseCase(donorRepository);
  }

  @Bean
  public UpdateDonorUseCase updateDonorUseCase(
      DonorRepository donorRepository, FindDonorByIdUseCase findDonorByIdUseCase) {
    return new UpdateDonorUseCase(donorRepository, findDonorByIdUseCase);
  }

  @Bean
  public DisableDonorUseCase deleteDonorUseCase(
      UserRepository userRepository,
      FindDonorByIdUseCase findDonorByIdUseCase,
      RecordAuditUseCase recordAuditUseCase) {
    return new DisableDonorUseCase(findDonorByIdUseCase, userRepository, recordAuditUseCase);
  }

  @Bean
  public DonorRepository donorRepository(
      JpaDonorRepository jpaDonorRepository, DonorEntityMappper donorEntityMapper) {
    return new DonorRepositoryImpl(jpaDonorRepository, donorEntityMapper);
  }
}
