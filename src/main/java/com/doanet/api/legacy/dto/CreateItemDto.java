package com.doanet.api.legacy.dto;

import com.doanet.api.legacy.validation.ValidExpirationDate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@ValidExpirationDate
public record CreateItemDto (
  @NotBlank
  String name,

  String description,

  boolean isPerishable,

  @Future
  LocalDate expirationDate
  ) {}
