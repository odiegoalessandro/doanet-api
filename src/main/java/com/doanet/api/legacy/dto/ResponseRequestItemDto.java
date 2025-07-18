package com.doanet.api.legacy.dto;


public record ResponseRequestItemDto(
  Long itemId,
  String itemName,
  Integer quantity
) {}
