package com.doanet.api.dto;

import com.doanet.api.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
