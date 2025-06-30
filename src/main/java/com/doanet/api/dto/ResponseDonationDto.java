package com.doanet.api.dto;

import java.time.LocalDate;
import java.util.List;

public record ResponseDonationDto(
  Long id,
  Long donorId,
  Long donationPointId,
  LocalDate createdAt,
  List<ResponseDonationItemDto> items
) {}
