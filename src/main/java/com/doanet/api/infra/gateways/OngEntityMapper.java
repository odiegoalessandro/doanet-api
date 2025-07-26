package com.doanet.api.infra.gateways;

import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.infra.persistence.OngEntity;
import org.springframework.stereotype.Component;

@Component
public class OngEntityMapper {
  private final UserEntityMapper userMapper;

  public OngEntityMapper(UserEntityMapper userMapper) {
    this.userMapper = userMapper;
  }

  public OngEntity toEntity(Ong ong){
    var user = this.userMapper.toEntity(ong.getUser());

    return new OngEntity(
      user,
      ong.getCnpj()
    );
  }

  public Ong toDomain(OngEntity ongEntity){
    var user = this.userMapper.toDomain(ongEntity.getUser());

    return new Ong(
      ongEntity.getId(),
      user,
      ongEntity.getCnpj()
    );
  }
}
