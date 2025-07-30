package com.doanet.api.config;

import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.application.usecases.donation.*;
import com.doanet.api.application.usecases.donationpoint.FindActiveDonationPointByIdUseCase;
import com.doanet.api.application.usecases.donor.FindActiveDonorByIdUseCase;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.infra.gateways.DonationEntityMapper;
import com.doanet.api.infra.gateways.DonationRepositoryImpl;
import com.doanet.api.infra.persistence.JpaDonationRepository;
import com.doanet.api.infra.persistence.JpaItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DonationConfig {
  @Bean
  public CreateDonationUseCase createDonationUseCase(
    DonationRepository donationRepository,
    FindActiveDonorByIdUseCase findActiveDonorByIdUseCase,
    FindActiveDonationPointByIdUseCase findActiveDonationPointByIdUseCase,
    FindItemByIdUseCase findItemByIdUseCase
  ){
    return new CreateDonationUseCase(
      donationRepository,
      findActiveDonorByIdUseCase,
      findActiveDonationPointByIdUseCase,
      findItemByIdUseCase
    );
  }

  @Bean
  public FindAllDonationsUseCase findAllDonationsUseCase(
    DonationRepository donationRepository
  ){
    return new FindAllDonationsUseCase(donationRepository);
  }

  @Bean
  public FindDonationByDonationPointIdUseCase findDonationByDonationPointIdUseCase(
    DonationRepository donationRepository
  ){
    return new FindDonationByDonationPointIdUseCase(donationRepository);
  }

  @Bean
  public FindDonationByDonorIdUseCase findDonationByDonorIdUseCase(DonationRepository donationRepository){
    return new FindDonationByDonorIdUseCase(donationRepository);
  }

  @Bean
  public FindDonationByIdUseCase findDonationByIdUseCase(DonationRepository donationRepository){
    return new FindDonationByIdUseCase(donationRepository);
  }

  @Bean
  public FindDonationByStatusUseCase findDonationByStatusUseCase(DonationRepository donationRepository){
    return new FindDonationByStatusUseCase(donationRepository);
  }

  @Bean
  public UpdateStatusDonationUseCase updateDonationStatusUseCase(
    DonationRepository donationRepository,
    FindDonationByIdUseCase findDonationByIdUseCase
  ){
    return new UpdateStatusDonationUseCase(donationRepository, findDonationByIdUseCase);
  }

  @Bean
  public UpdateDonationItemsUseCase updateDonationItemsUseCase(
    DonationRepository donationRepository,
    FindDonationByIdUseCase findDonationByIdUseCase,
    FindItemByIdUseCase findItemByIdUseCase
  ){
    return new UpdateDonationItemsUseCase(donationRepository, findDonationByIdUseCase, findItemByIdUseCase);
  }

  @Bean
  public DonationRepository donationRepository(
    JpaDonationRepository jpaDonationRepository,
    DonationEntityMapper donationEntityMapper,
    JpaItemRepository jpaItemRepository
  ){
    return new DonationRepositoryImpl(donationEntityMapper, jpaDonationRepository, jpaItemRepository);
  }
}
