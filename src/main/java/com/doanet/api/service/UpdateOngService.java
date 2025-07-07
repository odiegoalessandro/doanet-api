package com.doanet.api.service;

import com.doanet.api.dto.UpdateOngDto;
import com.doanet.api.entity.Ong;
import com.doanet.api.mapper.UpdateOngMapper;
import com.doanet.api.repository.OngRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateOngService {
  private FindOngService findOngService;
  private OngRepository ongRepository;
  private UpdateOngMapper updateOngMapper;

  public UpdateOngService(FindOngService findOngService, OngRepository ongRepository, UpdateOngMapper updateOngMapper){
    this.findOngService = findOngService;
    this.ongRepository = ongRepository;
    this.updateOngMapper = updateOngMapper;
  }

  public Ong update(Long ongId, UpdateOngDto updateOngDto){
    Ong ong = this.findOngService.findById(ongId);
    updateOngMapper.updateFromDto(updateOngDto, ong);

    return this.ongRepository.save(ong);
  }
}
