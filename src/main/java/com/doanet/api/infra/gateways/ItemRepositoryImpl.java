package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.dto.Pagination;
import com.doanet.api.application.gateways.ItemRepository;
import com.doanet.api.domain.entities.item.Item;
import com.doanet.api.infra.persistence.JpaItemRepository;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class ItemRepositoryImpl implements ItemRepository {
  private final JpaItemRepository itemRepository;
  private final ItemEntityMapper mapper;

  public ItemRepositoryImpl(JpaItemRepository itemRepository, ItemEntityMapper mapper){
    this.itemRepository = itemRepository;
    this.mapper = mapper;
  }

  @Override
  public Item save(Item item) {
    var entity = this.mapper.toEntity(item);
    var saved = this.itemRepository.save(entity);

    return this.mapper.toDomain(saved);
  }

  @Override
  public PageResponse<Item> findByNameContainingIgnoreCase(String name, Pagination pagination) {
    var pageNumber = Math.max(1, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.itemRepository.findByNameContainingIgnoreCase(name, pageable);
    var dtoPage = jpaPage.map(this.mapper::toDomain);

    return new PageResponse<Item>(
      dtoPage.getContent(),
      dtoPage.getTotalElements(),
      dtoPage.getTotalPages(),
      dtoPage.getNumber() + 1
    );
  }

  @Override
  public Optional<Item> findById(Long id) {
    return this.itemRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public PageResponse<Item> findAll(Pagination pagination) {
    var pageNumber = Math.max(1, pagination.page()) - 1;
    var pageable = PageRequest.of(pageNumber, pagination.size());
    var jpaPage = this.itemRepository.findAll(pageable);
    var dtoPage = jpaPage.map(this.mapper::toDomain);

    return new PageResponse<Item>(
      dtoPage.getContent(),
      dtoPage.getTotalElements(),
      dtoPage.getTotalPages(),
      dtoPage.getNumber() + 1
    );
  }

  @Override
  public void deleteById(Long itemId) {
    this.itemRepository.deleteById(itemId);
  }
}
