package com.doanet.api.application.commands;

public record UpdateDonationPointCommand(
  String name,
  String email,
  String phone,
  String description
) {
}
