package com.doanet.api.config;

import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.application.usecases.donationpoint.*;
import com.doanet.api.infra.gateways.DonationPointEntityMapper;
import com.doanet.api.infra.gateways.DonationPointRepositoryImpl;
import com.doanet.api.infra.persistence.JpaDonationPointRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DonationPointConfig {
  @Bean
  public CreateDonationPointUseCase createDonationPointUseCase(DonationPointRepository donationPointRepository) {
    return new CreateDonationPointUseCase(donationPointRepository);
  }

  @Bean
  public FindDonationPointByIdUseCase findActiveDonationPointByIdUseCase(
    DonationPointRepository donationPointRepository
  ) {
    return new FindDonationPointByIdUseCase(donationPointRepository);
  }

  @Bean
  public FindDonationPointByDescriptionUseCase findActiveDonationPointByDescriptionUseCase(
    DonationPointRepository donationPointRepository
  ){
    return new FindDonationPointByDescriptionUseCase(donationPointRepository);
  }

  @Bean
  public FindAllDonationPointsUseCase findAllDonationPointsUseCase(
    DonationPointRepository donationPointRepository
  ) {
    return new FindAllDonationPointsUseCase(donationPointRepository);
  }

  @Bean
  public UpdateDonationPointUseCase updateDonationPointUseCase(
    DonationPointRepository donationPointRepository,
    FindDonationPointByIdUseCase findDonationPointByIdUseCase
  ){
    return new UpdateDonationPointUseCase(donationPointRepository, findDonationPointByIdUseCase);
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

