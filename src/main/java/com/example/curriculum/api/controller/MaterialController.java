package com.example.curriculum.api.controller;

import com.example.curriculum.api.dto.MaterialUpdateRequest;
import com.example.curriculum.persistence.entity.Material;
import com.example.curriculum.persistence.entity.User;
import com.example.curriculum.security.UserPrincipal;
import com.example.curriculum.service.FileStorageService;
import com.example.curriculum.service.MaterialService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/materials")
public class MaterialController {

    private final FileStorageService fileStorageService;
    private final MaterialService materialService;

    @PutMapping("/{id}")
    public ResponseEntity<Material> updateMaterial(@PathVariable Long id,
                                                     @Valid @RequestBody MaterialUpdateRequest request,
                                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User currentUser = userPrincipal.getUser();
        Material updatedMaterial = materialService.updateMaterial(id, request, currentUser);
        return ResponseEntity.ok(updatedMaterial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                // Fallback
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
