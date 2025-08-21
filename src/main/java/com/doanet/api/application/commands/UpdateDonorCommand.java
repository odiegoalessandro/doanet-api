package com.doanet.api.application.commands;

public record UpdateDonorCommand(
  String name,
  String email,
  String phone
) {
}
