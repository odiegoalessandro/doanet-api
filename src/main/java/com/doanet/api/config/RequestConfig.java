package com.doanet.api.config;

import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.donationpoint.FindDonationPointByIdUseCase;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.application.usecases.ong.FindOngByIdUseCase;
import com.doanet.api.application.usecases.request.*;
import com.doanet.api.infra.gateways.RequestEntityMapper;
import com.doanet.api.infra.gateways.RequestRepositoryImpl;
import com.doanet.api.infra.persistence.JpaItemRepository;
import com.doanet.api.infra.persistence.JpaRequestRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestConfig {

  @Bean
  public CreateRequestUseCase createRequestUseCase(
      RequestRepository requestRepository,
      FindOngByIdUseCase findOngByIdUseCase,
      FindDonationPointByIdUseCase findDonationPointByIdUseCase,
      FindItemByIdUseCase findItemByIdUseCase,
      RecordAuditUseCase recordAuditUseCase) {
    return new CreateRequestUseCase(
        requestRepository,
        findOngByIdUseCase,
        findDonationPointByIdUseCase,
        findItemByIdUseCase,
        recordAuditUseCase);
  }

  @Bean
  public FindAllRequestsUseCase findAllRequestsUseCase(RequestRepository requestRepository) {
    return new FindAllRequestsUseCase(requestRepository);
  }

  @Bean
  public FindRequestByDonationPointIdUseCase findRequestByDonationPointId(
      RequestRepository requestRepository) {
    return new FindRequestByDonationPointIdUseCase(requestRepository);
  }

  @Bean
  public FindRequestByIdUseCase findRequestByIdUseCase(RequestRepository requestRepository) {
    return new FindRequestByIdUseCase(requestRepository);
  }

  @Bean
  public FindRequestByOngIdUseCase findRequestByOngIdUseCase(RequestRepository requestRepository) {
    return new FindRequestByOngIdUseCase(requestRepository);
  }

  @Bean
  public FindRequestByStatusUseCase findRequestByStatusUseCase(
      RequestRepository requestRepository) {
    return new FindRequestByStatusUseCase(requestRepository);
  }

  @Bean
  public UpdateRequestItemUseCase updateRequestItemUseCase(
      RequestRepository requestRepository,
      FindRequestByIdUseCase findRequestByIdUseCase,
      FindItemByIdUseCase findItemByIdUseCase) {
    return new UpdateRequestItemUseCase(
        requestRepository, findRequestByIdUseCase, findItemByIdUseCase);
  }

  @Bean
  public UpdateRequestStatusUseCase updateRequestStatusUseCase(
      RequestRepository requestRepository,
      FindRequestByIdUseCase findRequestByIdUseCase,
      RecordAuditUseCase recordAuditUseCase) {
    return new UpdateRequestStatusUseCase(
        requestRepository, findRequestByIdUseCase, recordAuditUseCase);
  }

  @Bean
  public RequestRepository requestRepository(
      JpaRequestRepository jpaRequestRepository,
      RequestEntityMapper requestEntityMapper,
      JpaItemRepository jpaItemRepository) {
    return new RequestRepositoryImpl(jpaRequestRepository, requestEntityMapper, jpaItemRepository);
  }
}
