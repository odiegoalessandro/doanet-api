package com.doanet.api.application.commands;

public record CreateDonationItemCommand(
  Long productId,
  Integer quantity
) {}
