package com.doanet.api.infra.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto (
  Long id,
  String email,
  String phone,
  String street,
  String number,
  String neighborhood,
  String name,
  String city,
  String state,
  String zipCode
){
}
