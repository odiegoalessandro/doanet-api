package com.doanet.api.infra.controller;

public record DonationPointDto(
  UserDto user,
  String description
) {
}
