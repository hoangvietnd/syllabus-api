package com.example.curriculum.persistence.repository;

import com.example.curriculum.persistence.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
