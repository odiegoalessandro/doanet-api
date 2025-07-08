package com.doanet.api.service;

import com.doanet.api.entity.Ong;
import com.doanet.api.repository.OngRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FindOngService {
  private final OngRepository ongRepository;

  public FindOngService(OngRepository ongRepository) {
    this.ongRepository = ongRepository;
  }

  public Ong findById(Long id){
    return this.ongRepository.findByIdActive(id)
        .orElseThrow(() -> new EntityNotFoundException("No ONG found with id " + id));
  }

  public Ong findByCnpj(String cnpj){
    return this.ongRepository.findByCnpjActive(cnpj)
        .orElseThrow(() -> new EntityNotFoundException("No ONG found with CNPJ " + cnpj));
  }

  public Page<Ong> findAll(Integer pageNumber, Integer pageSize){
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.ongRepository.findAllActive(pageable);
  }
}
