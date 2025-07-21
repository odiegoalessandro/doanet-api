package com.doanet.api.legacy.service;

import com.doanet.api.infra.persistence.ItemEntity;
import com.doanet.api.infra.persistence.JpaItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindItemService {
  private JpaItemRepository itemRepository;

  public FindItemService(JpaItemRepository itemRepository){
    this.itemRepository = itemRepository;
  }

  public ItemEntity findById(Long id){
    return this.itemRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("No item found with the id " + id));
  }

  public Page<ItemEntity> findByName(String name, int pageNumber, int pageSize){
     Pageable pageable = PageRequest.of(pageNumber, pageSize);

    return this.itemRepository.findByNameContainingIgnoreCase(name, pageable);
  }

  public Page<ItemEntity> findAll(int pageNumber, int pageSize){
    var pageable = PageRequest.of(pageNumber, pageSize);

    return this.itemRepository.findAll(pageable);
  }
}
