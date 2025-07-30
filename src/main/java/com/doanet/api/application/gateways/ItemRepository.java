package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.domain.entities.item.Item;

import java.util.Optional;

public interface ItemRepository {
  Item save(Item item);
  PageResponse<Item> findByNameContainingIgnoreCase(String name, Pagination pagination);
  Optional<Item> findById(Long id);
  PageResponse<Item> findAll(Pagination pagination);

  void deleteById(Long itemId);
}
