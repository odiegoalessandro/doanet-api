package com.doanet.api.application.usecases.item;

import com.doanet.api.application.gateways.ItemRepository;
import com.doanet.api.domain.entities.item.Item;

public class FindItemByIdUseCase {
  private final ItemRepository itemRepository;

  public FindItemByIdUseCase(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public Item execute(Long itemId) {
    return itemRepository.findById(itemId)
        .orElseThrow(() -> new IllegalArgumentException("Item não foi encontrado com o ID: " + itemId));
  }
}
