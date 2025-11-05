package com.example.curriculum.api.dto;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private List<String> tags;
    private List<MaterialDto> materials;
    private Integer version;
    private String createdBy; // Changed from Long to String
    private Instant createdAt;
    private Instant updatedAt;

}
