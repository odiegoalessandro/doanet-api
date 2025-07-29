package com.doanet.api.infra.controller;

import java.util.List;

public record DonationDto (
  Long id,
  Long donorId,
  Long donationPointId,
  List<DonationItemDto> items
){ }
