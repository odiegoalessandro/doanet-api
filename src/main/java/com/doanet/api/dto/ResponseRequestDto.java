package com.doanet.api.dto;


import java.time.LocalDate;
import java.util.List;

public record ResponseRequestDto(
  Long id,
  Long ongId,
  Long donationPointId,
  LocalDate createdAt,
  List<ResponseRequestItemDto> items
) {}
