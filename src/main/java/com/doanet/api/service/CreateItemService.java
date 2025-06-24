package com.doanet.api.service;

import com.doanet.api.dto.CreateItemDto;
import com.doanet.api.entity.Item;
import com.doanet.api.repository.ItemRepository;
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
