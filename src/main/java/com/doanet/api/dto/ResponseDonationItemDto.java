package com.doanet.api.dto;

public record ResponseDonationItemDto(
  Long itemId,
  String itemName,
  Integer quantity
) {}