package com.doanet.api.legacy.validation;

import com.doanet.api.legacy.dto.CreateItemDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ExpirationDateValidator implements ConstraintValidator<ValidExpirationDate, CreateItemDto> {
  @Override
  public boolean isValid(CreateItemDto dto, ConstraintValidatorContext context) {
    if (!dto.isPerishable()) {
      return true;
    }

    return dto.expirationDate() != null && dto.expirationDate().isAfter(LocalDate.now());
  }
}

