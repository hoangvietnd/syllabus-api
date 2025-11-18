package com.example.curriculum.service;

import com.example.curriculum.api.dto.MaterialUpdateRequest;
import com.example.curriculum.persistence.entity.Material;
import com.example.curriculum.persistence.entity.User;
import com.example.curriculum.persistence.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Transactional
    public Material updateMaterial(Long id, MaterialUpdateRequest request, User currentUser) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found with id: " + id));

        material.setDescription(request.getDescription());
        material.setUpdatedBy(currentUser);
        // The updatedAt field will be updated automatically by @UpdateTimestamp

        return materialRepository.save(material);
    }

    @Transactional
    public void deleteMaterial(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new EntityNotFoundException("Material not found with id: " + id);
        }
        materialRepository.deleteById(id);
    }
}
