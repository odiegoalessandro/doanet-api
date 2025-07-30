package com.doanet.api.application.usecases.item;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.ItemRepository;
import com.doanet.api.domain.entities.item.Item;

public class FindAllItemsUseCase {
  private final ItemRepository itemRepository;

  public FindAllItemsUseCase(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public PageResponse<Item> execute(int page, int size) {
    var pagination = new Pagination(page, size);

    return itemRepository.findAll(pagination);
  }
}
