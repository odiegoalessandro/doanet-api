package com.doanet.api.legacy.dto;

import jakarta.validation.constraints.Email;

public record UpdateDonationPointDto(
  String name,
  @Email
  String email,
  String phone,
  String description
) {
}
