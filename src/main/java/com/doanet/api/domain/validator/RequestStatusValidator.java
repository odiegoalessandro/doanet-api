package com.doanet.api.domain.validator;

import com.doanet.api.domain.enums.RequestStatus;

import java.util.Set;

public class RequestStatusValidator {
  private final static Set<RequestStatus> NOT_ALLOWED_STATUSES = Set.of(
    RequestStatus.BLOCKED,
    RequestStatus.DELIVERED,
    RequestStatus.EXPIRED,
    RequestStatus.CANCELLED
  );

  public static void validate(RequestStatus status) {
    if (NOT_ALLOWED_STATUSES.contains(status)) {
      throw new IllegalArgumentException("Não é possivel atualizar a solicitação com o status: " + status);
    }
  }
}
