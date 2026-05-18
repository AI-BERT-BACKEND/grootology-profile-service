package com.aibert.dosw.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FileUploadServiceTest {

    @InjectMocks
    private FileUploadService fileUploadService;

    @Test
    void upload_archivoMayorA2MB_lanzaException() {
        MockMultipartFile file = new MockMultipartFile(
                "photo", "foto.jpg", "image/jpeg", new byte[3 * 1024 * 1024]);
        assertThrows(IllegalArgumentException.class, () -> fileUploadService.upload(file));
    }

    @Test
    void upload_archivoValido_retornaRuta() throws Exception {
        byte[] content = "fake-image-content".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "photo", "foto.jpg", "image/jpeg", content);

        String result = fileUploadService.upload(file);

        assertNotNull(result);
        assertTrue(result.startsWith("uploads/profiles/"));
        assertTrue(result.endsWith("_foto.jpg"));
    }

    @Test
    void upload_nombreArchivoConPath_usaSoloNombre() throws Exception {
        byte[] content = "fake-image-content".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "photo", "subdir/foto.png", "image/png", content);

        String result = fileUploadService.upload(file);

        assertNotNull(result);
        assertTrue(result.startsWith("uploads/profiles/"));
    }
}
