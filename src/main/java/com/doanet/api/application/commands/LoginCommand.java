package com.doanet.api.application.commands;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email deve ter padrão válido")
  String email,

  @NotBlank(message = "Senha é obrigatória")
  String password
) {}
