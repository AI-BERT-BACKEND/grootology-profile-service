package com.aibert.dosw.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    private static final long MAX_SIZE = 2 * 1024 * 1024L;
    private static final String UPLOAD_DIR = "uploads/profiles/";

    public String upload(MultipartFile file) {
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("La imagen no puede superar 2 MB");
        }
        try {
            Path dir = Paths.get(UPLOAD_DIR);
            Files.createDirectories(dir);
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), dir.resolve(filename));
            return UPLOAD_DIR + filename;
        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen", e);
        }
    }
}
