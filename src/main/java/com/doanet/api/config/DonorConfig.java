package com.doanet.api.config;

import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.application.usecases.donor.*;
import com.doanet.api.application.usecases.user.CreateUserUseCase;
import com.doanet.api.infra.gateways.DonorEntityMappper;
import com.doanet.api.infra.gateways.DonorRepositoryImpl;
import com.doanet.api.infra.persistence.JpaDonorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DonorConfig {
  @Bean
  public CreateDonorUseCase createDonorUseCase(DonorRepository donorRepository, CreateUserUseCase createUserUseCase){
    return new CreateDonorUseCase(donorRepository, createUserUseCase);
  }

  @Bean
  public FindActiveDonorByIdUseCase findActiveDonorByIdUseCase(DonorRepository donorRepository) {
    return new FindActiveDonorByIdUseCase(donorRepository);
  }

  @Bean
  public FindDonorByDocumentUseCase findDonorByDocumentUseCase(DonorRepository donorRepository) {
    return new FindDonorByDocumentUseCase(donorRepository);
  }

  @Bean
  public FindDonorByReasonSocialUseCase findDonorByReasonSocialUseCase(DonorRepository donorRepository) {
    return new FindDonorByReasonSocialUseCase(donorRepository);
  }

  @Bean
  public FindActiveDonorByDocumentUseCase findActiveDonorByDocumentUseCase(DonorRepository donorRepository) {
    return new FindActiveDonorByDocumentUseCase(donorRepository);
  }

  @Bean
  public FindActiveDonorByReasonSocialUseCase findActiveDonorByReasonSocialUseCase(DonorRepository donorRepository) {
    return new FindActiveDonorByReasonSocialUseCase(donorRepository);
  }

  @Bean
  public FindAllActiveDonorsUseCase findAllActiveDonorsUseCase(DonorRepository donorRepository) {
    return new FindAllActiveDonorsUseCase(donorRepository);
  }

  @Bean
  public UpdateDonorUseCase updateDonorUseCase(
    DonorRepository donorRepository,
    FindActiveDonorByIdUseCase findActiveDonorByIdUseCase
  ) {
    return new UpdateDonorUseCase(donorRepository, findActiveDonorByIdUseCase);
  }

  @Bean
  public DeleteDonorUseCase deleteDonorUseCase(
    DonorRepository donorRepository,
    FindActiveDonorByIdUseCase findActiveDonorByIdUseCase
  ) {
    return new DeleteDonorUseCase(findActiveDonorByIdUseCase, donorRepository);
  }

  @Bean
  public DonorRepository donorRepository(JpaDonorRepository jpaDonorRepository, DonorEntityMappper donorEntityMapper) {
    return new DonorRepositoryImpl(jpaDonorRepository, donorEntityMapper);
  }
}
