package com.example.curriculum.api.controller;

import com.example.curriculum.api.dto.CourseDto;
import com.example.curriculum.api.mapper.CourseMapper;
import com.example.curriculum.persistence.entity.Course;
import com.example.curriculum.service.CourseService;
import com.example.curriculum.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService service;
    private final CourseMapper mapper;

    @PostMapping
    public ResponseEntity<CourseDto> create(@RequestBody @Valid CourseDto dto,
            @AuthenticationPrincipal UserPrincipal user) {
        Course created = service.create(mapper.toCourse(dto), user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(created));
    }

    @GetMapping
    public Page<CourseDto> list(@RequestParam Optional<String> q, Pageable pageable) {
        return (q.isPresent()
                ? service.findByTitle(q.get(), pageable)
                : service.list(pageable)).map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> update(@PathVariable Long id,
            @RequestBody @Valid CourseDto dto,
            @AuthenticationPrincipal UserPrincipal user) {
        // user is available if you need to check for permissions
        Course updated = service.update(id, mapper.toCourse(dto));
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal user) {
        service.delete(id);
    }
}
