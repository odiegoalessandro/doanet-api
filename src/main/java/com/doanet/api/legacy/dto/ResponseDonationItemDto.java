package com.doanet.api.legacy.dto;

public record ResponseDonationItemDto(
  Long itemId,
  String itemName,
  Integer quantity
) {}