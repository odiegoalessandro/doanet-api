package com.doanet.api.legacy.dto;

import java.util.List;

public record UpdateRequestItemsDto(
  List<UpdateRequestItemDto> items
) {
}
