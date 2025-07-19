package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.Coordinates;

public interface GetCoordinatesByAddress {
  Coordinates execute(String address);
}
