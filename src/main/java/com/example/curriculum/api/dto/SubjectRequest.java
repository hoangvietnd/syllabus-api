package com.example.curriculum.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest {

    @NotBlank(message = "Subject name cannot be blank")
    @Size(max = 255, message = "Subject name cannot exceed 255 characters")
    private String name;

    private String description;
}
