package com.doanet.api.infra.controller;

public record OngDto (
  Long id,
  String cnpj,
  UserDto user
){ }
