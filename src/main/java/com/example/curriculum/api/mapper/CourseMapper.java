package com.example.curriculum.api.mapper;

import com.example.curriculum.api.dto.CourseDto;
import com.example.curriculum.persistence.entity.Course;
import com.example.curriculum.persistence.entity.User;
import com.example.curriculum.persistence.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = MaterialMapper.class)
public abstract class CourseMapper {

    @Autowired
    protected UserRepository userRepository;

    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "materials", target = "materials")
    public abstract CourseDto toDto(Course course);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "materials", ignore = true)
    public abstract Course toCourse(CourseDto dto);

    User map(Long value) {
        return userRepository.findById(value).orElse(null);
    }
}
