package com.doanet.api.application.usecases.item;

import com.doanet.api.application.gateways.ItemRepository;

public class DeleteItemByIdUseCase {
  private final ItemRepository itemRepository;

  public DeleteItemByIdUseCase(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public void execute(Long itemId) {
    this.itemRepository.deleteById(itemId);
  }
}
