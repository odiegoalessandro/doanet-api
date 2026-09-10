package com.doanet.api.config;

import com.doanet.api.application.gateways.GetCoordinatesByAddress;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.user.DisableUserUseCase;
import com.doanet.api.application.usecases.user.GeolocatePendingUsersUseCase;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.infra.gateways.UserEntityMapper;
import com.doanet.api.infra.gateways.UserRepositoryImpl;
import com.doanet.api.infra.persistence.JpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
  @Bean
  public GeolocateUserUseCase geolocateUserUseCase(GetCoordinatesByAddress getCoordinatesByAddress) {
    return new GeolocateUserUseCase(getCoordinatesByAddress);
  }

  @Bean
  public GeolocatePendingUsersUseCase geolocatePendingUsersUseCase(
    UserRepository userRepository,
    GeolocateUserUseCase geolocateUserUseCase
  ) {
    return new GeolocatePendingUsersUseCase(userRepository, geolocateUserUseCase);
  }

  @Bean
  public DisableUserUseCase disableUserUseCase(
    UserRepository userRepository,
    RecordAuditUseCase recordAuditUseCase
  ) {
    return new DisableUserUseCase(userRepository, recordAuditUseCase);
  }

  @Bean
  public UserRepositoryImpl userRepositoryImpl(JpaUserRepository jpaUserRepository, UserEntityMapper userEntityMapper) {
    return new UserRepositoryImpl(jpaUserRepository, userEntityMapper);
  }
}
