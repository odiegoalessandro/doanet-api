package com.doanet.api.dto;

import java.util.List;

public record UpdateDonationItemsDto(
  List<UpdateDonationItemDto> items
) {
}
