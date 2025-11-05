package com.example.curriculum.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String filePath;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fileType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long courseId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;
}
