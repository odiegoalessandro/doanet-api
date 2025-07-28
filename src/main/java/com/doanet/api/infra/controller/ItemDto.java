package com.doanet.api.infra.controller;

import java.time.LocalDate;

public record ItemDto (
  Long id,
  String name,
  String description,
  boolean isPerishable,
  LocalDate expirationDate
){ }
