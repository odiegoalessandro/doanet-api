package com.doanet.api.legacy.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateDonationPointDto(
    @Valid
    CreateUserDto user,

    @NotBlank
    String description
) {
}
