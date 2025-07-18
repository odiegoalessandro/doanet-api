package com.doanet.api.legacy.dto;

import com.doanet.api.legacy.entity.DonationPoint;
import com.doanet.api.legacy.entity.Donor;

import java.time.LocalDate;
import java.util.List;

public record DetailedResponseDonationDto (
  Long id,
  Donor donor,
  DonationPoint donationPoint,
  LocalDate createdAt,
  List<ResponseDonationItemDto> items
){
}
