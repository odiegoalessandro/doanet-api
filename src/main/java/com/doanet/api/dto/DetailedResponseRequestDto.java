package com.doanet.api.dto;

import com.doanet.api.entity.DonationPoint;
import com.doanet.api.entity.Ong;

import java.time.LocalDate;
import java.util.List;

public record DetailedResponseRequestDto(
  Long id,
  Ong ong,
  DonationPoint donationPoint,
  LocalDate createdAt,
  List<ResponseRequestItemDto> items
) {
}
