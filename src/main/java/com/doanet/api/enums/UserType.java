package com.doanet.api.enums;

import lombok.Getter;

@Getter
public enum UserType {
  DONOR("DONOR"),
  ONG("ONG"),
  DONATION_POINT("DONATION_POINT");

  private final String value;

  UserType(String value) {
    this.value = value;
  }

}
