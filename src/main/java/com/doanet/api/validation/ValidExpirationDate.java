package com.doanet.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExpirationDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpirationDate {
  String message() default "expirationDate must be present and in the future if isPerishable is true";

  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
