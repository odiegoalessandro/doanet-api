package com.doanet.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CNPJ;

public record CreateOngDto(
    @Valid
    CreateUserDto user,

    @NotNull
    String cnpj) {
}
