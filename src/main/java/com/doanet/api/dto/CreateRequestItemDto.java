package com.doanet.api.dto;

import jakarta.validation.constraints.NotNull;

public record CreateRequestItemDto(
  @NotNull
  Long itemId,
  @NotNull
  Integer quantity
) {
}
