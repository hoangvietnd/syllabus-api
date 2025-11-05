package com.example.curriculum.api.controller;

import com.example.curriculum.api.dto.SubjectRequest;
import com.example.curriculum.api.dto.SubjectResponse;
import com.example.curriculum.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @PostMapping
    public ResponseEntity<SubjectResponse> createSubject(@Valid @RequestBody SubjectRequest subjectRequest) {
        SubjectResponse createdSubject = subjectService.createSubject(subjectRequest);
        return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponse> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequest subjectRequest) {
        return ResponseEntity.ok(subjectService.updateSubject(id, subjectRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}
