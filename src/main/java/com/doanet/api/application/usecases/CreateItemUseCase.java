package com.doanet.api.application.usecases;

import com.doanet.api.application.gateways.ItemRepository;
import com.doanet.api.domain.entities.item.Item;

public class CreateItemUseCase {
  private final ItemRepository itemRepository;

  public CreateItemUseCase(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public Item execute(Item item){
    return this.itemRepository.save(item);
  }
}
