package com.doanet.api.application.gateways;

import com.doanet.api.domain.entities.user.User;

public interface AccessTokenIssuer {
  String issue(User user);

  long accessTokenTtlSeconds();
}
