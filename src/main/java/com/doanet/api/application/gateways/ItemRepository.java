package com.doanet.api.application.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.domain.entities.user.Item;

public interface ItemRepository {
  Item save(Item item);
  PageResponse<Item> findByNameContainingIgnoreCase(String name, Pagination pagination);
}
