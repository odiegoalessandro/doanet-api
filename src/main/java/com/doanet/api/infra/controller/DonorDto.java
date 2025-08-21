package com.doanet.api.infra.controller;

public record DonorDto(
  Long id,
  String document,
  String reasonSocial,
  UserDto user
) {
}
