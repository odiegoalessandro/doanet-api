package com.doanet.api.mapper;

import com.doanet.api.dto.UpdateDonationPointDto;
import com.doanet.api.entity.DonationPoint;
import org.mapstruct.*;

@Mapper(
  componentModel = "spring",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UpdateDonationPointMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user.id", ignore = true)
  @Mapping(target = "user.name", source = "name")
  @Mapping(target = "user.email", source = "email")
  @Mapping(target = "user.phone", source = "phone")
  void updateFromDto(UpdateDonationPointDto dto, @MappingTarget DonationPoint entity);

}
