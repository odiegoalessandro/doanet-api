package com.doanet.api.infra.controller;

public record DonationPointDto(
  Long id,
  UserDto user,
  String description
) {
}
