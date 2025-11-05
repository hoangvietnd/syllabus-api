package com.example.curriculum.api.mapper;

import com.example.curriculum.api.dto.CourseDto;
import com.example.curriculum.persistence.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MaterialMapper.class)
public interface CourseMapper {

    @Mapping(source = "createdBy.fullName", target = "createdBy")
    @Mapping(source = "materials", target = "materials")
    CourseDto toDto(Course course);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "materials", ignore = true)
    // We need to add other fields to ignore to avoid unmapped target properties warnings
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "subject", ignore = true)
    Course toCourse(CourseDto dto);
}
