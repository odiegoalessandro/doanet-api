package com.doanet.api.application.gateways;

import com.doanet.api.domain.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
  User save(User user);
  void disableUser(Long id);
  List<User> findAllWithoutCoordinates();
  Optional<User> findByEmail(String email);
  Optional<User> findById(Long id);
}
