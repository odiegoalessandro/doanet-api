package com.doanet.api.dto;

public record UpdateRequestItemDto(
  Long itemId,
  Integer quantity
) {
}
