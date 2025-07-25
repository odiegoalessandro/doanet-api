package com.doanet.api.application.commands;

public record RequestItemCommand(
  Long itemId,
  Integer quantity
) { }
