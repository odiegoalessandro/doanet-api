package com.doanet.api.infra.controller;

public record RequestItemDto (
  Long id,
  Long itemId,
  Integer quantity
){
}
