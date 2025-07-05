package com.doanet.api.service;

import com.doanet.api.dto.DetailedResponseRequestDto;
import com.doanet.api.dto.ResponseRequestDto;
import com.doanet.api.mapper.RequestMapper;
import com.doanet.api.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FindRequestService {
  private final RequestRepository requestRepository;
  private final RequestMapper requestMapper;

  public FindRequestService(RequestRepository requestRepository, RequestMapper requestMapper) {
    this.requestRepository = requestRepository;
    this.requestMapper = requestMapper;
  }

  public DetailedResponseRequestDto findDtoById(Long id) {
    var request = this.requestRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("No request found with id " + id));

    return requestMapper.toDetailedResponse(request);
  }

  public Page<ResponseRequestDto> findAllDto(int pageNumber, int pageSize) {
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.requestRepository.findAll(pageable)
      .map(requestMapper::toResponse);
  }

  public Page<ResponseRequestDto> findByOngIdDto(Long ongId, int pageNumber, int pageSize) {
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.requestRepository.findByOngId(ongId, pageable)
      .map(requestMapper::toResponse);
  }

  public Page<ResponseRequestDto> findByDonationPointIdDto(Long donationPointId, int pageNumber, int pageSize) {
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.requestRepository.findByDonationPointId(donationPointId, pageable)
      .map(requestMapper::toResponse);
  }
}
