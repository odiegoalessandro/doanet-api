package com.doanet.api.application.commands;

public record CreateDonationPointCommand(
  String name,
  String email,
  String password,
  String phone,
  String street,
  String number,
  String neighborhood,
  String city,
  String state,
  String zipCode,
  Double latitude,
  Double longitude,
  String description
) {
}
