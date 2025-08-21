package com.doanet.api.domain.validator;

import com.doanet.api.domain.enums.DonationStatus;

import java.util.Set;

public class DonationStatusValidator {
  private final static Set<DonationStatus> NOT_ALLOWED_STATUSES = Set.of(
    DonationStatus.BLOCKED,
    DonationStatus.DELIVERED,
    DonationStatus.EXPIRED,
    DonationStatus.CANCELLED
  );

  public static void validate(DonationStatus status) {
    if (NOT_ALLOWED_STATUSES.contains(status)) {
      throw new IllegalArgumentException("Não é possivel atualizar a doação com o status: " + status);
    }
  }
}
