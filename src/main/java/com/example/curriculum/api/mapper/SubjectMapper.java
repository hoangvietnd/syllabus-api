package com.example.curriculum.api.mapper;

import com.example.curriculum.api.dto.SubjectRequest;
import com.example.curriculum.api.dto.SubjectResponse;
import com.example.curriculum.persistence.entity.Subject;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(source = "createdBy.fullName", target = "createdBy")
    @Mapping(source = "updatedBy.fullName", target = "updatedBy")
    SubjectResponse toSubjectResponse(Subject subject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true) // Ignore courses collection
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Subject toSubject(SubjectRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true) // Ignore courses collection
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubjectFromRequest(SubjectRequest request, @MappingTarget Subject subject);
}
