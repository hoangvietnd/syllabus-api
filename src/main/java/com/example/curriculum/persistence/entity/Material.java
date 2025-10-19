package com.example.curriculum.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "materials")
@Getter
@Setter
public class Material {

    public enum Type {
        PDF, VIDEO, LINK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Type type;

    @Column(name = "url_or_path", nullable = false, length = 1024)
    private String urlOrPath;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "mime_type", length = 255)
    private String mimeType;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

}

