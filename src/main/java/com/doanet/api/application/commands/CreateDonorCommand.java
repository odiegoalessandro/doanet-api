package com.doanet.api.application.commands;

public record CreateDonorCommand(
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
  String reasonSocial,
  String document
) {}
