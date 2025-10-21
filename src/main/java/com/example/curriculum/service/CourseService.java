package com.example.curriculum.service;

import com.example.curriculum.persistence.entity.Course;
import com.example.curriculum.persistence.repository.CourseRepository;
import com.example.curriculum.persistence.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CourseService {
    private final CourseRepository repo;
    private final UserRepository userRepo;

    public CourseService(CourseRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
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
}
