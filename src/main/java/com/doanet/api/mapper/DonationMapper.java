package com.doanet.api.mapper;

import com.doanet.api.dto.DetailedResponseDonationDto;
import com.doanet.api.dto.ResponseDonationDto;
import com.doanet.api.dto.ResponseDonationItemDto;
import com.doanet.api.entity.Donation;
import com.doanet.api.entity.DonationItem;
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
      donation.getDonationPoint().getId(),
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
      donation.getDonationPoint(),
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
