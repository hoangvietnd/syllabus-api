package com.example.curriculum.api.mapper;

import com.example.curriculum.api.dto.MaterialDto;
import com.example.curriculum.persistence.entity.Material;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "fileType", ignore = true) // Will be set in the service
    @Mapping(target = "createdAt", ignore = true) // createdAt is auto-generated
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Material toEntity(MaterialDto dto);

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "createdBy.fullName", target = "createdBy")
    @Mapping(source = "updatedBy.fullName", target = "updatedBy")
    MaterialDto toDto(Material entity);
}
