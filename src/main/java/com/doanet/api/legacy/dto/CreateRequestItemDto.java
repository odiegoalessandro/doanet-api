package com.doanet.api.legacy.dto;

import jakarta.validation.constraints.NotNull;

public record CreateRequestItemDto(
  @NotNull
  Long itemId,
  @NotNull
  Integer quantity
) {
}
