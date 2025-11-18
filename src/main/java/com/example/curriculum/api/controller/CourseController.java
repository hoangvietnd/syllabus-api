package com.example.curriculum.api.controller;

import com.example.curriculum.api.dto.CourseDto;
import com.example.curriculum.api.dto.MaterialDto;
import com.example.curriculum.api.mapper.CourseMapper;
import com.example.curriculum.api.mapper.MaterialMapper;
import com.example.curriculum.persistence.entity.Course;
import com.example.curriculum.persistence.entity.Material;
import com.example.curriculum.persistence.entity.User;
import com.example.curriculum.security.UserPrincipal;
import com.example.curriculum.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService service;
    private final CourseMapper mapper;
    private final MaterialMapper materialMapper;

    @PostMapping(path = "/create-with-material", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CourseDto> createWithMaterial(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(name = "material", required = false) MultipartFile material,
            @AuthenticationPrincipal UserPrincipal userPrincipal) throws IOException {
        User currentUser = userPrincipal.getUser();
        Course createdCourse = service.createCourseWithMaterial(title, description, currentUser, material);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(createdCourse));
    }

    @PostMapping
    public ResponseEntity<CourseDto> create(@RequestBody @Valid CourseDto dto,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User currentUser = userPrincipal.getUser();
        Course created = service.create(mapper.toCourse(dto), currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> findById(@PathVariable Long id) {
        Course course = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(course));
    }

    @GetMapping
    public Page<CourseDto> list(@RequestParam(name = "title", required = false) String title, Pageable pageable) {
        return ((title != null && !title.isBlank())
                ? service.findByTitle(title, pageable)
                : service.list(pageable)).map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> update(@PathVariable Long id,
                                            @RequestBody @Valid CourseDto dto,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User currentUser = userPrincipal.getUser();
        Course updated = service.update(id, dto, currentUser);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping(path = "/{courseId}/materials", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MaterialDto> addMaterial(
            @PathVariable Long courseId,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal userPrincipal) throws IOException {
        User currentUser = userPrincipal.getUser();
        Material savedMaterial = service.addMaterialToCourse(courseId, description, file, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(materialMapper.toDto(savedMaterial));
    }
}
