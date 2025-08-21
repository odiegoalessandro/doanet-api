package com.doanet.api.infra.controller;

import com.doanet.api.domain.enums.DonationStatus;

import java.util.List;

public record DonationDto (
  Long id,
  Long donorId,
  Long donationPointId,
  List<DonationItemDto> items,
  DonationStatus status
){ }
