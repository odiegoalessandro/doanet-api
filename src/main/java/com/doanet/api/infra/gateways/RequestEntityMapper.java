package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.infra.persistence.RequestEntity;
import com.doanet.api.infra.persistence.RequestItemEntity;

public class RequestEntityMapper {
  private final DonationPointEntityMapper donationPointMapper;
  private final OngEntityMapper ongMapper;
  private final RequestItemEntityMapper requestItemMapper;

  public RequestEntityMapper(DonationPointEntityMapper donationPointMapper, OngEntityMapper ongMapper,
                             RequestItemEntityMapper requestItemMapper) {
    this.donationPointMapper = donationPointMapper;
    this.ongMapper = ongMapper;
    this.requestItemMapper = requestItemMapper;
  }

  public RequestEntity toEntity(Request request){
    var requestEntity =  new RequestEntity(
      request.getId(),
      this.donationPointMapper.toEntity(request.getDonationPoint()),
      this.ongMapper.toEntity(request.getOng()),
      request.getCreatedAt(),
      null,
      request.getStatus()
    );

    var items = request.getItems().stream()
      .map(item -> {
        var entity = this.requestItemMapper.toEntity(item);
        entity.setRequest(requestEntity);

        return entity;
      })
      .toList();

    requestEntity.setItems(items);
    return requestEntity;
  }

  public Request toDomain(RequestEntity entity) {
    var request = new Request(
      entity.getId(),
      this.donationPointMapper.toDomain(entity.getDonationPoint()),
      this.ongMapper.toDomain(entity.getOng()),
      entity.getCreatedAt(),
      null,
      entity.getStatus()
    );

    var items = entity.getItems().stream()
      .map(item -> {
        var domainItem = this.requestItemMapper.toDomain(item);
        domainItem.setRequest(request);

        return domainItem;
      })
      .toList();

    request.setItems(items);
    return request;
  }
}
