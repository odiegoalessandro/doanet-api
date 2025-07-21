package com.doanet.api.legacy.service;

import com.doanet.api.legacy.dto.CreateItemDto;
import com.doanet.api.infra.persistence.ItemEntity;
import com.doanet.api.infra.persistence.JpaItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateItemService {
  private JpaItemRepository itemRepository;

  public CreateItemService(JpaItemRepository itemRepository){
    this.itemRepository = itemRepository;
  }

  @Transactional
  public ItemEntity create(CreateItemDto itemDto){
    return this.itemRepository.save(new ItemEntity());
  }
}
