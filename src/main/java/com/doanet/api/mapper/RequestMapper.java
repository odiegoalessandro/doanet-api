package com.doanet.api.mapper;

import com.doanet.api.dto.*;
import com.doanet.api.entity.DonationItem;
import com.doanet.api.entity.Request;
import com.doanet.api.entity.RequestItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestMapper {
  public ResponseRequestDto toResponse(Request request) {
    List<ResponseRequestItemDto> items = request.getRequestItems().stream()
      .map(this::toItemResponse)
      .toList();

    return new ResponseRequestDto(
      request.getId(),
      request.getOng().getId(),
      request.getDonationPoint().getId(),
      request.getCreatedAt(),
      items
    );
  }

  public DetailedResponseRequestDto toDetailedResponse(Request request){
    List<ResponseRequestItemDto> items = request.getRequestItems().stream()
      .map(this::toItemResponse)
      .toList();

    return new DetailedResponseRequestDto(
      request.getId(),
      request.getOng(),
      request.getDonationPoint(),
      request.getCreatedAt(),
      items
    );
  }

  private ResponseRequestItemDto toItemResponse(RequestItem item) {
    return new ResponseRequestItemDto(
      item.getItem().getId(),
      item.getItem().getName(),
      item.getQuantity()
    );
  }
}
