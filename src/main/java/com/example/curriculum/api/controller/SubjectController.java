package com.example.curriculum.api.controller;

import com.example.curriculum.api.dto.SubjectRequest;
import com.example.curriculum.api.dto.SubjectResponse;
import com.example.curriculum.persistence.entity.User;
import com.example.curriculum.security.UserPrincipal;
import com.example.curriculum.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public Page<SubjectResponse> getAllSubjects(@RequestParam(name = "name", required = false) String name, Pageable pageable) {
        return ((name != null && !name.isBlank())
                ? subjectService.findByName(name, pageable)
                : subjectService.getAllSubjects(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @PostMapping
    public ResponseEntity<SubjectResponse> createSubject(@Valid @RequestBody SubjectRequest subjectRequest,
                                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User currentUser = userPrincipal.getUser();
        SubjectResponse createdSubject = subjectService.createSubject(subjectRequest, currentUser);
        return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponse> updateSubject(@PathVariable Long id,
                                                       @Valid @RequestBody SubjectRequest subjectRequest,
                                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User currentUser = userPrincipal.getUser();
        return ResponseEntity.ok(subjectService.updateSubject(id, subjectRequest, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}
