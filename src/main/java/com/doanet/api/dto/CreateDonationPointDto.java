package com.doanet.api.dto;

import com.doanet.api.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateDonationPointDto(
    @Valid
    CreateUserDto user,

    @NotBlank
    String description
) {
}
