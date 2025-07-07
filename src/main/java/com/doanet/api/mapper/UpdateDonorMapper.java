package com.doanet.api.mapper;

import com.doanet.api.dto.UpdateDonorDto;
import com.doanet.api.entity.Donor;
import org.mapstruct.*;

@Mapper(
  componentModel = "spring",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UpdateDonorMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user.id", ignore = true)
  @Mapping(target = "user.name", source = "name")
  @Mapping(target = "user.email", source = "email")
  @Mapping(target = "user.phone", source = "phone")
  void updateFromDto(UpdateDonorDto dto, @MappingTarget Donor entity);

}
