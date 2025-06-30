package com.doanet.api.dto;

import com.doanet.api.entity.DonationPoint;
import com.doanet.api.entity.Donor;

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
