package com.doanet.api.legacy.service;

import com.doanet.api.legacy.dto.CreateItemDto;
import com.doanet.api.legacy.entity.Item;
import com.doanet.api.legacy.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateItemService {
  private ItemRepository itemRepository;

  public CreateItemService(ItemRepository itemRepository){
    this.itemRepository = itemRepository;
  }

  @Transactional
  public Item create(CreateItemDto itemDto){
    return this.itemRepository.save(new Item(itemDto));
  }
}
