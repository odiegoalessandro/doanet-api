package com.doanet.api.infra.controller;

import java.util.List;

public record RequestDto(
  Long id,
  Long ongId,
  Long donationPointId,
  List<RequestItemDto> items
) {
}
