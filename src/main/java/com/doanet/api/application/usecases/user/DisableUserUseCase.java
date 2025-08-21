package com.doanet.api.application.usecases.user;

import com.doanet.api.application.gateways.UserRepository;

public class DisableUserUseCase {
  private final UserRepository userRepository;

  public DisableUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void execute(Long id){
    this.userRepository.disableUser(id);
  }
}
