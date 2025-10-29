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
    Material toEntity(MaterialDto dto);

    @Mapping(source = "course.id", target = "courseId")
    MaterialDto toDto(Material entity);
}
