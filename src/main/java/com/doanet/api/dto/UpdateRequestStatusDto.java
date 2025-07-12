package com.doanet.api.dto;

import com.doanet.api.enums.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateRequestStatusDto(
  @NotNull Status status
) {}
