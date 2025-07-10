package com.doanet.api.dto;

import jakarta.validation.constraints.Email;

public record UpdateDonationPointDto(
  String name,
  @Email
  String email,
  String phone,
  String description
) {
}
