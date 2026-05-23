package com.aibert.dosw.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "jwt.secret=test_jwt_secret_min_32_characters_123456",
        "app.base-url=http://localhost:1501",
        "spring.mail.host=localhost",
        "spring.mail.port=2525",
        "spring.mail.username=test@mail.escuelaing.edu.co",
        "spring.mail.password=test-password"
})
@AutoConfigureMockMvc
class SecurityConfigIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void authEndpoint_esPublico_yNoExigeJWT() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void adminEndpoint_sinToken_retorna403() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void internalEndpoint_esPublico_yPasaSeguridad() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(get("/internal/users/{id}", randomId))
                .andExpect(status().isNotFound());
    }
}
