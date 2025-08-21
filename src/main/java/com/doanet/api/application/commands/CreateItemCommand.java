package com.doanet.api.application.commands;

import java.time.LocalDate;

public record CreateItemCommand(
  String name,
  String description,
  boolean isPerishable,
  LocalDate expirationDate
) {
}
