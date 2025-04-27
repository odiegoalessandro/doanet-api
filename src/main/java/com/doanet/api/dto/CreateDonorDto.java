package com.doanet.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateDonorDto(
    @NotBlank
  String document,
  @NotBlank
  String reasonSocial,
  @Valid
  CreateUserDto user
) {
}
