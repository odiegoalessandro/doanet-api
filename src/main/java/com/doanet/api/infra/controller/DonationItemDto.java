package com.doanet.api.infra.controller;

public record DonationItemDto(
  Long id,
  Long itemId,
  Integer quantity
) { }
