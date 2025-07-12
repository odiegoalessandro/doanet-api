package com.doanet.api.dto;

import java.util.List;

public record UpdateRequestItemsDto(
  List<UpdateRequestItemDto> items
) {
}
