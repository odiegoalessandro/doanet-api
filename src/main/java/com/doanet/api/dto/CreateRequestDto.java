package com.doanet.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateRequestDto (
  @NotNull
  Long ongId,

  @NotNull
  Long donationPointId,

  @NotEmpty
  @Valid
  List<CreateRequestItemDto> items
){
}
