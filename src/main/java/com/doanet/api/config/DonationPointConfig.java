package com.doanet.api.config;

import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.donationpoint.*;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
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
    GeolocateUserUseCase geolocateUserUseCase,
    PasswordHasher passwordHasher
  ) {
    return new CreateDonationPointUseCase(donationPointRepository, geolocateUserUseCase, passwordHasher);
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
  public DisableDonationPointUseCase deleteDonationPointByIdUseCase(
    UserRepository userRepository,
    FindDonationPointByIdUseCase findDonationPointByIdUseCase
  ) {
    return new DisableDonationPointUseCase(userRepository, findDonationPointByIdUseCase);
  }

  @Bean
  public DonationPointRepository donationPointRepositoryImpl(
    JpaDonationPointRepository jpaDonationPointRepository,
    DonationPointEntityMapper donationPointEntityMapper
  ) {
    return new DonationPointRepositoryImpl(jpaDonationPointRepository, donationPointEntityMapper);
  }
}

