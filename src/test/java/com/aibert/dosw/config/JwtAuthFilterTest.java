package com.aibert.dosw.config;

import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @Mock private UserRepositoryPort userRepository;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain chain;

    @InjectMocks private JwtAuthFilter filter;

    private static final String SECRET = "wwEejxjgXFljx8rgl2axPocLobwRjRQnlgfeLLn9/24DNkFsiZgJweFc1ArLuydrraB3uhINOLGB1zJgRGWpRg==";
    private Key key;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(filter, "secret", SECRET);
        key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        SecurityContextHolder.clearContext();
    }

    private String buildToken(String email, String userId, String role, Integer passwordVersion) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .claim("passwordVersion", passwordVersion)
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Test
    void rutaAuth_saltaFiltro() throws Exception {
        when(request.getRequestURI()).thenReturn("/api/auth/login");
        filter.doFilterInternal(request, response, chain);
        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void rutaSwagger_saltaFiltro() throws Exception {
        when(request.getRequestURI()).thenReturn("/swagger-ui/index.html");
        filter.doFilterInternal(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void rutaActuator_saltaFiltro() throws Exception {
        when(request.getRequestURI()).thenReturn("/actuator/health");
        filter.doFilterInternal(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void sinHeader_noAutentica() throws Exception {
        when(request.getRequestURI()).thenReturn("/api/profile/me");
        when(request.getHeader("Authorization")).thenReturn(null);
        filter.doFilterInternal(request, response, chain);
        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void headerSinBearer_noAutentica() throws Exception {
        when(request.getRequestURI()).thenReturn("/api/profile/me");
        when(request.getHeader("Authorization")).thenReturn("Basic abc123");
        filter.doFilterInternal(request, response, chain);
        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void tokenValido_passwordVersionCoincide_autentica() throws Exception {
        UUID userId = UUID.randomUUID();
        String token = buildToken("user@mail.com", userId.toString(), "ESTUDIANTE", 1);
        User user = User.builder().id(userId).passwordVersion(1).build();

        when(request.getRequestURI()).thenReturn("/api/profile/me");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("user@mail.com");
    }

    @Test
    void tokenValido_passwordVersionDiferente_noAutentica() throws Exception {
        UUID userId = UUID.randomUUID();
        String token = buildToken("user@mail.com", userId.toString(), "ESTUDIANTE", 1);
        User user = User.builder().id(userId).passwordVersion(2).build();

        when(request.getRequestURI()).thenReturn("/api/profile/me");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void tokenValido_usuarioNoEncontrado_autenticaIgual() throws Exception {
        UUID userId = UUID.randomUUID();
        String token = buildToken("user@mail.com", userId.toString(), "ADMIN", 1);

        when(request.getRequestURI()).thenReturn("/api/admin/users");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
    }

    @Test
    void tokenInvalido_limpiaContexto() throws Exception {
        when(request.getRequestURI()).thenReturn("/api/profile/me");
        when(request.getHeader("Authorization")).thenReturn("Bearer tokenbasura");

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}
