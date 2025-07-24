package com.doanet.api.application.commands;

public record DonationItemCommand(
  Long itemId,
  Integer quantity
) {
}
