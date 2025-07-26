package com.doanet.api.config;

import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.application.usecases.donationpoint.*;
import com.doanet.api.application.usecases.user.CreateUserUseCase;
import com.doanet.api.infra.gateways.DonationPointEntityMapper;
import com.doanet.api.infra.gateways.DonationPointRepositoryImpl;
import com.doanet.api.infra.persistence.JpaDonationPointRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DonationPointConfig {
  @Bean
  public CreateDonationPointUseCase createDonationPointUseCase(
    DonationPointRepository donationPointRepository,
    CreateUserUseCase createUserUseCase
  ) {
    return new CreateDonationPointUseCase(donationPointRepository, createUserUseCase);
  }

  @Bean
  public FindActiveDonationPointByIdUseCase findActiveDonationPointByIdUseCase(
    DonationPointRepository donationPointRepository
  ) {
    return new FindActiveDonationPointByIdUseCase(donationPointRepository);
  }

  @Bean
  public FindActiveDonationPointByDescriptionUseCase findActiveDonationPointByDescriptionUseCase(
    DonationPointRepository donationPointRepository
  ){
    return new FindActiveDonationPointByDescriptionUseCase(donationPointRepository);
  }

  @Bean
  public FindAllActiveDonationPointsUseCase findAllActiveDonationPointsUseCase(
    DonationPointRepository donationPointRepository
  ) {
    return new FindAllActiveDonationPointsUseCase(donationPointRepository);
  }

  @Bean
  public UpdateDonationPointUseCase updateDonationPointUseCase(
    DonationPointRepository donationPointRepository,
    FindActiveDonationPointByIdUseCase findActiveDonationPointByIdUseCase
  ){
    return new UpdateDonationPointUseCase(donationPointRepository, findActiveDonationPointByIdUseCase);
  }

  @Bean
  public DeleteDonationPointUseCase deleteDonationPointByIdUseCase(
    DonationPointRepository donationPointRepository
  ) {
    return new DeleteDonationPointUseCase(donationPointRepository);
  }

  @Bean
  public DonationPointRepository donationPointRepositoryImpl(
    JpaDonationPointRepository jpaDonationPointRepository,
    DonationPointEntityMapper donationPointEntityMapper
  ) {
    return new DonationPointRepositoryImpl(jpaDonationPointRepository, donationPointEntityMapper);
  }
}

