package com.doanet.api.dto;

import jakarta.validation.constraints.Email;

public record UpdateDonorDto (
  String name,
  @Email
  String email,
  String phone
){
}
