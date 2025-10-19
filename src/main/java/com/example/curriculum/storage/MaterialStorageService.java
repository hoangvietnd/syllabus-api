package com.example.curriculum.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MaterialStorageService {
    private final Path root = Paths.get("uploads");

    public String save(MultipartFile file) throws IOException {
        Files.createDirectories(root);
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path target = root.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toString(); // prod: lưu S3 URL
    }
}
