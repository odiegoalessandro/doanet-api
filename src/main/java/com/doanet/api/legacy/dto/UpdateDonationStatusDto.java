package com.doanet.api.legacy.dto;

import com.doanet.api.legacy.enums.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateDonationStatusDto(
  @NotNull
  Status status
) {
}
