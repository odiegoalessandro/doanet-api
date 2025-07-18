package com.doanet.api.legacy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
    @Email
    @NotBlank
    String email,

    @NotBlank
    String password,

    @NotBlank
    String phone,

    @NotBlank
    String street,

    @NotBlank
    String number,

    @NotBlank
    String neighborhood,

    @NotBlank
    String name,

    @NotBlank
    String city,

    @NotBlank
    String state,

    @NotBlank
    String zipCode
) {
}
