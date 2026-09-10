package com.doanet.api.application.commands;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenCommand(
  @NotBlank(message = "Refresh token é obrigatório")
  String refreshToken
) {}
