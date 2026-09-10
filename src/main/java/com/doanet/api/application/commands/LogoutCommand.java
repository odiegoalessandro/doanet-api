package com.doanet.api.application.commands;

import jakarta.validation.constraints.NotBlank;

public record LogoutCommand(
  @NotBlank(message = "Refresh token é obrigatório")
  String refreshToken
) {}
