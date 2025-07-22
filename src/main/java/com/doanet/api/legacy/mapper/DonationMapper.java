package com.doanet.api.legacy.mapper;

import com.doanet.api.legacy.dto.DetailedResponseDonationDto;
import com.doanet.api.legacy.dto.ResponseDonationDto;
import com.doanet.api.legacy.dto.ResponseDonationItemDto;
import com.doanet.api.legacy.entity.Donation;
import com.doanet.api.legacy.entity.DonationItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DonationMapper {

  public ResponseDonationDto toResponse(Donation donation) {
    List<ResponseDonationItemDto> items = donation.getDonationItems().stream()
      .map(this::toItemResponse)
      .toList();

    return new ResponseDonationDto(
      donation.getId(),
      donation.getDonor().getId(),
      donation.getDonationPointEntity().getId(),
      donation.getCreatedAt(),
      items
    );
  }

  public DetailedResponseDonationDto toDetailedResponse(Donation donation){
    List<ResponseDonationItemDto> items = donation.getDonationItems().stream()
      .map(this::toItemResponse)
      .toList();

    return new DetailedResponseDonationDto(
      donation.getId(),
      donation.getDonor(),
      donation.getDonationPointEntity(),
      donation.getCreatedAt(),
      items
    );
  }

  private ResponseDonationItemDto toItemResponse(DonationItem item) {
    return new ResponseDonationItemDto(
      item.getItem().getId(),
      item.getItem().getName(),
      item.getQuantity()
    );
  }
}
