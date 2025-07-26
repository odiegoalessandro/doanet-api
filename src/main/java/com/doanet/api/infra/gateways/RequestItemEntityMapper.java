package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.request.RequestItem;
import com.doanet.api.infra.persistence.RequestItemEntity;
import org.springframework.stereotype.Component;

@Component
public class RequestItemEntityMapper {
  private final ItemEntityMapper itemMapper;

  public RequestItemEntityMapper(ItemEntityMapper itemMapper) {
    this.itemMapper = itemMapper;
  }

  public RequestItemEntity toEntity(RequestItem requestItem) {
    return new RequestItemEntity(
      requestItem.getId(),
      null,
      this.itemMapper.toEntity(requestItem.getItem()),
      requestItem.getQuantity()
    );
  }

  public RequestItem toDomain(RequestItemEntity requestItemEntity) {
    return new RequestItem(
      requestItemEntity.getId(),
      null,
      this.itemMapper.toDomain(requestItemEntity.getItem()),
      requestItemEntity.getQuantity()
    );
  }
}
