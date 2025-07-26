package com.doanet.api.application.usecases.item;

import com.doanet.api.application.commands.CreateItemCommand;
import com.doanet.api.application.gateways.ItemRepository;
import com.doanet.api.domain.entities.item.Item;

import java.time.LocalDate;

public class CreateItemUseCase {
  private final ItemRepository itemRepository;

  public CreateItemUseCase(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public Item execute(CreateItemCommand itemCommand) {
    var item = new Item(
      null,
      itemCommand.name(),
      itemCommand.description(),
      itemCommand.isPerishable(),
      LocalDate.now()
    );

    return itemRepository.save(item);
  }
}
