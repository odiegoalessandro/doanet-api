package com.doanet.api.application.gateways;

import com.doanet.api.domain.entities.user.User;

public interface UserRepository {
  User save(User user);
  void disableUser(Long id);
}
