package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.gateways.UserRepository;

public class DisableOngByIdUseCase {
  private final UserRepository userRepository;
  private final FindOngByIdUseCase findOngByIdUseCase;

  public DisableOngByIdUseCase(
     UserRepository userRepository,
     FindOngByIdUseCase findOngByIdUseCase
  ){
    this.userRepository = userRepository;
    this.findOngByIdUseCase = findOngByIdUseCase;
  }

  public void execute(Long id){
    var ong = this.findOngByIdUseCase.execute(id, true);

    this.userRepository.disableUser(ong.getUser().getId());
  }
}
