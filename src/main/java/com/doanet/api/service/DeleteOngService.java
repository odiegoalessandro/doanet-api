package com.doanet.api.service;

import com.doanet.api.entity.Ong;
import com.doanet.api.repository.OngRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteOngService {
  private FindOngService findOngService;
  private OngRepository ongRepository;

  public DeleteOngService(FindOngService findOngService, OngRepository ongRepository){
    this.findOngService = findOngService;
    this.ongRepository = ongRepository;
  }

  public Ong delete(Long ongId){
    var ong = this.findOngService.findById(ongId);

    ong.getUser().setActive(false);

    return this.ongRepository.save(ong);
  }
}
