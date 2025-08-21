package com.doanet.api.application.usecases.item;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.ItemRepository;
import com.doanet.api.domain.entities.item.Item;
import org.springframework.data.domain.PageRequest;

public class FindItemByNameUseCase {
  private final ItemRepository itemRepository;

  public FindItemByNameUseCase(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public PageResponse<Item> execute(String name, int page, int size) {
    var pagination = new Pagination(page, size);

    return itemRepository.findByNameContainingIgnoreCase(name, pagination);
  }
}
