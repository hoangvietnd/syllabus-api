package com.example.curriculum.service;

import com.example.curriculum.api.dto.SubjectRequest;
import com.example.curriculum.api.dto.SubjectResponse;
import com.example.curriculum.api.mapper.SubjectMapper;
import com.example.curriculum.persistence.entity.Subject;
import com.example.curriculum.persistence.entity.User;
import com.example.curriculum.persistence.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Transactional(readOnly = true)
    public Page<SubjectResponse> getAllSubjects(Pageable pageable) {
        return subjectRepository.findAll(pageable)
                .map(subjectMapper::toSubjectResponse);
    }

    @Transactional(readOnly = true)
    public Page<SubjectResponse> findByName(String name, Pageable pageable) {
        return subjectRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(subjectMapper::toSubjectResponse);
    }

    @Transactional(readOnly = true)
    public SubjectResponse getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .map(subjectMapper::toSubjectResponse)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + id));
    }

    @Transactional
    public SubjectResponse createSubject(SubjectRequest subjectRequest, User currentUser) {
        Subject subject = subjectMapper.toSubject(subjectRequest);

        // Set audit fields
        subject.setCreatedBy(currentUser);
        subject.setUpdatedBy(currentUser);

        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.toSubjectResponse(savedSubject);
    }

    @Transactional
    public SubjectResponse updateSubject(Long id, SubjectRequest subjectRequest, User currentUser) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + id));

        subjectMapper.updateSubjectFromRequest(subjectRequest, existingSubject);

        // Set updatedBy field
        existingSubject.setUpdatedBy(currentUser);

        Subject updatedSubject = subjectRepository.save(existingSubject);
        return subjectMapper.toSubjectResponse(updatedSubject);
    }

    @Transactional
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new EntityNotFoundException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
    }
}
