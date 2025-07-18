package com.doanet.api.legacy.dto;

import java.util.List;

public record UpdateDonationItemsDto(
  List<UpdateDonationItemDto> items
) {
}
