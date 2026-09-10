package com.doanet.api.application.gateways;

public interface TokenFactory {
  String generate();

  String hash(String rawToken);
}
