package com.doanet.api.legacy.dto;

import com.doanet.api.legacy.entity.DonationPointEntity;
import com.doanet.api.legacy.entity.Donor;

import java.time.LocalDate;
import java.util.List;

public record DetailedResponseDonationDto (
  Long id,
  Donor donor,
  DonationPointEntity donationPointEntity,
  LocalDate createdAt,
  List<ResponseDonationItemDto> items
){
}
