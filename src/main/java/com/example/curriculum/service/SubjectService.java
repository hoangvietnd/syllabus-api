package com.example.curriculum.service;

import com.example.curriculum.api.dto.SubjectRequest;
import com.example.curriculum.api.dto.SubjectResponse;
import com.example.curriculum.api.mapper.SubjectMapper;
import com.example.curriculum.persistence.entity.Subject;
import com.example.curriculum.persistence.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Transactional(readOnly = true)
    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toSubjectResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubjectResponse getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .map(subjectMapper::toSubjectResponse)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + id));
    }

    @Transactional
    public SubjectResponse createSubject(SubjectRequest subjectRequest) {
        Subject subject = subjectMapper.toSubject(subjectRequest);
        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.toSubjectResponse(savedSubject);
    }

    @Transactional
    public SubjectResponse updateSubject(Long id, SubjectRequest subjectRequest) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + id));

        subjectMapper.updateSubjectFromRequest(subjectRequest, existingSubject);
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
