package com.doanet.api.service;

import com.doanet.api.dto.UpdateDonorDto;
import com.doanet.api.entity.Donor;
import com.doanet.api.mapper.UpdateDonorMapper;
import com.doanet.api.repository.DonorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateDonorService {
  private FindDonorService findDonorService;
  private DonorRepository donorRepository;
  private UpdateDonorMapper updateDonorMapper;

  @Transactional
  public Donor update(Long donorId, UpdateDonorDto updateDonorDto){
    var donor = this.findDonorService.findById(donorId);
    this.updateDonorMapper.updateFromDto(updateDonorDto, donor);

    return this.donorRepository.save(donor);
  }

}
