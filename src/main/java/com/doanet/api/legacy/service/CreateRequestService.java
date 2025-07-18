package com.doanet.api.legacy.service;

import com.doanet.api.legacy.dto.CreateRequestDto;
import com.doanet.api.legacy.dto.CreateRequestItemDto;
import com.doanet.api.legacy.dto.ResponseRequestDto;
import com.doanet.api.legacy.entity.Request;
import com.doanet.api.legacy.entity.RequestItem;
import com.doanet.api.legacy.mapper.RequestMapper;
import com.doanet.api.legacy.repository.RequestItemRepository;
import com.doanet.api.legacy.repository.RequestRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreateRequestService {
  private RequestRepository requestRepository;
  private RequestItemRepository requestItemRepository;
  private FindOngService findOngService;
  private FindDonationPointService findDonationPointService;
  private FindItemService findItemService;
  private RequestMapper requestMapper;

  public CreateRequestService(
    RequestRepository requestRepository,
    RequestItemRepository requestItemRepository,
    FindOngService findOngService,
    FindDonationPointService findDonationPointService,
    FindItemService findItemService,
    RequestMapper requestMapper
  ){
    this.requestRepository = requestRepository;
    this.requestItemRepository = requestItemRepository;
    this.findOngService = findOngService;
    this.findDonationPointService = findDonationPointService;
    this.findItemService = findItemService;
    this.requestMapper = requestMapper;
  }

  @NotNull
  public ResponseRequestDto create(CreateRequestDto requestDto){
    var ong = this.findOngService.findById(requestDto.ongId());
    var donationPoint = this.findDonationPointService.findById(requestDto.donationPointId());
    var request = new Request(donationPoint, ong, LocalDate.now());

    this.requestRepository.save(request);

    for(CreateRequestItemDto itemDto : requestDto.items()){
      var item = this.findItemService.findById(itemDto.itemId());
      var requestItem = new RequestItem(request, item, itemDto.quantity());

      this.requestItemRepository.save(requestItem);
      request.getRequestItems().add(requestItem);
    }

    return requestMapper.toResponse(request);
  }
}
