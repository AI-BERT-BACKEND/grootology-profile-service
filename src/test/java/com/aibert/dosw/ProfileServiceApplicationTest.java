package com.aibert.dosw;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "jwt.secret=test_jwt_secret_min_32_characters_123456",
        "app.base-url=http://localhost:1501",
        "spring.mail.host=localhost",
        "spring.mail.port=2525",
        "spring.mail.username=test@mail.escuelaing.edu.co",
        "spring.mail.password=test-password"
})
class ProfileServiceApplicationTest {

    @Test
    void contextLoads() {
    }
}
