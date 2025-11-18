package com.example.curriculum.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialUpdateRequest {
    @NotBlank(message = "Description cannot be blank")
    private String description;
}
