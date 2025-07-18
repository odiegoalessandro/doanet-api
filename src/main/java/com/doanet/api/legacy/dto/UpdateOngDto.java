package com.doanet.api.legacy.dto;

import jakarta.validation.constraints.Email;

public record UpdateOngDto(
  String name,
  @Email
  String email,
  String phone
) {
}
