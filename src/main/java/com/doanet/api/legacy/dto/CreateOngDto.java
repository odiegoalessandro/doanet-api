package com.doanet.api.legacy.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateOngDto(
    @Valid
    CreateUserDto user,

    @NotNull
    String cnpj) {
}
