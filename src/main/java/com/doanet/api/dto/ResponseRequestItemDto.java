package com.doanet.api.dto;


public record ResponseRequestItemDto(
  Long itemId,
  String itemName,
  Integer quantity
) {}
