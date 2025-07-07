package com.doanet.api.service;

import com.doanet.api.dto.CreateRequestDto;
import com.doanet.api.dto.CreateRequestItemDto;
import com.doanet.api.dto.ResponseRequestDto;
import com.doanet.api.entity.Request;
import com.doanet.api.entity.RequestItem;
import com.doanet.api.mapper.RequestMapper;
import com.doanet.api.repository.RequestItemRepository;
import com.doanet.api.repository.RequestRepository;
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
