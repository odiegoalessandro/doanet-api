package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.item.Item;
import com.doanet.api.infra.persistence.ItemEntity;

public class ItemEntityMapper {
  public ItemEntity toEntity(Item item) {
    return new ItemEntity(
      item.getName(),
      item.getDescription(),
      item.isPerishable(),
      item.getExpirationDate()
    );
  }

  public Item toDomain(ItemEntity itemEntity) {
    return new Item(
      itemEntity.getId(),
      itemEntity.getName(),
      itemEntity.getDescription(),
      itemEntity.isPerishable(),
      itemEntity.getExpirationDate()
    );
  }
}
