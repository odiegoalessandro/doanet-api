package com.doanet.api.legacy.mapper;

import com.doanet.api.legacy.dto.DetailedResponseRequestDto;
import com.doanet.api.legacy.dto.ResponseRequestDto;
import com.doanet.api.legacy.dto.ResponseRequestItemDto;
import com.doanet.api.legacy.entity.Request;
import com.doanet.api.legacy.entity.RequestItem;
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
