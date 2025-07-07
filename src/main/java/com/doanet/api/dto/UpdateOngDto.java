package com.doanet.api.dto;

import jakarta.validation.constraints.Email;

public record UpdateOngDto(
  String name,
  @Email
  String email,
  String phone
) {
}
