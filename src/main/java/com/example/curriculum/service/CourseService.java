package com.example.curriculum.service;

import com.example.curriculum.api.dto.CourseDto;
import com.example.curriculum.persistence.entity.Course;
import com.example.curriculum.persistence.entity.Material;
import com.example.curriculum.persistence.entity.Subject;
import com.example.curriculum.persistence.entity.User;
import com.example.curriculum.persistence.repository.CourseRepository;
import com.example.curriculum.persistence.repository.MaterialRepository;
import com.example.curriculum.persistence.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CourseService {
    private final CourseRepository repo;
    private final MaterialRepository materialRepo;
    private final FileStorageService fileStorageService;
    private final SubjectRepository subjectRepo;

    public CourseService(CourseRepository repo, MaterialRepository materialRepo, FileStorageService fileStorageService, SubjectRepository subjectRepo) {
        this.repo = repo;
        this.materialRepo = materialRepo;
        this.fileStorageService = fileStorageService;
        this.subjectRepo = subjectRepo;
    }

    @Transactional
    public Course createCourseWithMaterial(String title, String description, User currentUser, MultipartFile file) throws IOException {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setCreatedBy(currentUser);
        course.setUpdatedBy(currentUser);
        // The version field defaults to 1 in the entity

        Course savedCourse = repo.save(course);

        if (file != null && !file.isEmpty()) {
            // Pass the current user to the material creation as well
            this.addMaterialToCourse(savedCourse.getId(), "Initial material", file, currentUser);
        }

        return savedCourse;
    }

    @Transactional
    public Material addMaterialToCourse(Long courseId, String description, MultipartFile file, User currentUser) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required for material.");
        }

        Course course = repo.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));

        // Since we are modifying the course by adding material, we should update its audit info
        course.setUpdatedBy(currentUser);
        course.setVersion(course.getVersion() + 1);
        repo.save(course);

        String filePath = fileStorageService.store(file);

        Material material = new Material();
        material.setName(file.getOriginalFilename());
        material.setDescription(description);
        material.setFilePath(filePath);
        material.setFileType(file.getContentType());
        material.setCourse(course);
        material.setCreatedBy(currentUser);
        material.setUpdatedBy(currentUser);

        return materialRepo.save(material);
    }

    @Transactional
    public Course create(Course course, User currentUser) {
        course.setCreatedBy(currentUser);
        course.setUpdatedBy(currentUser);
        // The version field defaults to 1 in the entity
        return repo.save(course);
    }

    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
    }

    @Transactional
    public Course update(Long id, CourseDto dto, User currentUser) {
        Course existingCourse = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        existingCourse.setTitle(dto.getTitle());
        existingCourse.setDescription(dto.getDescription());
        existingCourse.setTags(dto.getTags());

        if (dto.getSubjectId() != null) {
            Subject subject = subjectRepo.findById(dto.getSubjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + dto.getSubjectId()));
            existingCourse.setSubject(subject);
        }

        existingCourse.setVersion(existingCourse.getVersion() + 1);
        existingCourse.setUpdatedBy(currentUser);

        return repo.save(existingCourse);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Course not found with id: " + id);
        }
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Course> findByTitle(String title, Pageable pageable) {
        return repo.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Course> list(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
