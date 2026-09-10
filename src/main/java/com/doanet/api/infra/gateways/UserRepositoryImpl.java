package com.doanet.api.infra.gateways;

import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.infra.persistence.JpaUserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

  @Override
  public List<User> findAllWithoutCoordinates() {
    return this.jpaUserRepository.findAllWithoutCoordinates()
      .stream()
      .map(this.mapper::toDomain)
      .toList();
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return this.jpaUserRepository.findByEmail(email)
      .map(this.mapper::toDomain);
  }

  @Override
  public Optional<User> findById(Long id) {
    return this.jpaUserRepository.findById(id)
      .map(this.mapper::toDomain);
  }
}
