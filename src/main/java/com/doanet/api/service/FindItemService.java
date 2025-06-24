package com.doanet.api.service;

import com.doanet.api.entity.Item;
import com.doanet.api.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindItemService {
  private ItemRepository itemRepository;

  public FindItemService(ItemRepository itemRepository){
    this.itemRepository = itemRepository;
  }

  public Item findById(Long id){
    return this.itemRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("No item found with the id " + id));
  }

  public Page<Item> findByName(String name, int pageNumber, int pageSize){
     Pageable pageable = PageRequest.of(pageNumber, pageSize);

    return this.itemRepository.findByNameContainingIgnoreCase(name, pageable);
  }

  public Page<Item> findAll(int pageNumber, int pageSize){
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.itemRepository.findAll(pageable);
  }
}
