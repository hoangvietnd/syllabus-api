package com.example.curriculum.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class CourseDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String title;
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private SubjectResponse subject;

    private Long subjectId;

    private List<String> tags;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<MaterialDto> materials;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer version;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant updatedAt;
}
