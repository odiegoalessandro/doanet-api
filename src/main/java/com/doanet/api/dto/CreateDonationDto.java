package com.doanet.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateDonationDto(
  @NotNull
  Long donorId,

  @NotNull
  Long donationPointId,

  @NotEmpty
  List<CreateDonationItemDto> items
) {
}
