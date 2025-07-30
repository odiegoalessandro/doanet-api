package com.doanet.api.infra.controller;

import com.doanet.api.domain.enums.RequestStatus;

import java.util.List;

public record RequestDto(
  Long id,
  Long ongId,
  Long donationPointId,
  List<RequestItemDto> items,
  RequestStatus status
) {
}
