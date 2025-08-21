package com.doanet.api.infra.gateways;

import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.infra.persistence.JpaUserRepository;
import org.springframework.transaction.annotation.Transactional;

public class UserRepositoryImpl implements UserRepository {
  private final JpaUserRepository jpaUserRepository;
  private final UserEntityMapper mapper;

  public UserRepositoryImpl(JpaUserRepository jpaUserRepository, UserEntityMapper mapper) {
    this.jpaUserRepository = jpaUserRepository;
    this.mapper = mapper;
  }

  @Override
  public User save(User user) {
    var entity = this.mapper. toEntity(user);
    var savedUser = this.jpaUserRepository.save(entity);

    return this.mapper.toDomain(savedUser);
  }

  @Override
  @Transactional
  public void disableUser(Long id) {
    this.jpaUserRepository.disableUser(id);
  }
}
