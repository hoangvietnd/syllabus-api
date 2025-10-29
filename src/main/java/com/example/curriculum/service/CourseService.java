package com.example.curriculum.service;

import com.example.curriculum.persistence.entity.Course;
import com.example.curriculum.persistence.entity.Material;
import com.example.curriculum.persistence.repository.CourseRepository;
import com.example.curriculum.persistence.repository.MaterialRepository;
import com.example.curriculum.persistence.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.io.IOException;

@Service
public class CourseService {
    private final CourseRepository repo;
    private final UserRepository userRepo;
    private final MaterialRepository materialRepo;
    private final FileStorageService fileStorageService;

    public CourseService(CourseRepository repo, UserRepository userRepo, MaterialRepository materialRepo, FileStorageService fileStorageService) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.materialRepo = materialRepo;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public Course createCourseWithMaterial(String name, String description, Long userId, MultipartFile file) throws IOException {
        // Step 1: Create and save the course
        Course course = new Course();
        course.setTitle(name);
        course.setDescription(description);
        course.setVersion(1);
        course.setCreatedBy(userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
        course.setCreatedAt(Instant.now());
        course.setUpdatedAt(Instant.now());
        Course savedCourse = repo.save(course);

        // Step 2: If a file is provided, store it and create a material
        if (file != null && !file.isEmpty()) {
            String filePath = fileStorageService.store(file);

            Material material = new Material();
            material.setName(file.getOriginalFilename());
            material.setFilePath(filePath);
            material.setFileType(file.getContentType());
            material.setCourse(savedCourse);
            materialRepo.save(material);
            
            // Since the relationship is bidirectional, add material to the course's list
            savedCourse.getMaterials().add(material);
        }

        return savedCourse;
    }
    
    public Course create(Course c, Long userId) {
        c.setVersion(1);
        c.setCreatedBy(userRepo.findById(userId).orElseThrow());
        c.setCreatedAt(Instant.now());
        c.setUpdatedAt(Instant.now());
        return repo.save(c);
    }

    public Course update(Long id, Course update) {
        Course c = repo.findById(id).orElseThrow();
        c.setTitle(update.getTitle());
        c.setDescription(update.getDescription());
        c.setTags(update.getTags());
        c.setVersion(c.getVersion() + 1);
        c.setUpdatedAt(Instant.now());
        return repo.save(c);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Page<Course> findByTitle(String title, Pageable pageable) {
        return repo.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Course> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Transactional
    public Material addMaterialToCourse(Long courseId, Material material) {
        Course course = repo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        material.setCourse(course);
        return materialRepo.save(material);
    }
}
