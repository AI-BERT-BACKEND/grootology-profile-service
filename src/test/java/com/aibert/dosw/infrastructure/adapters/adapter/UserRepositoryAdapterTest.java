package com.aibert.dosw.infrastructure.adapters.adapter;

import com.aibert.dosw.domain.model.user.*;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.UserEntity;
import com.aibert.dosw.infrastructure.adapters.persistence.mapper.UserPersistenceMapper;
import com.aibert.dosw.infrastructure.adapters.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock private UserJpaRepository jpaRepository;
    @Mock private UserPersistenceMapper mapper;
    @InjectMocks private UserRepositoryAdapter adapter;

    private final UUID userId = UUID.randomUUID();

    private UserEntity buildEntity() {
        UserEntity e = new UserEntity();
        e.setId(userId);
        e.setEmail("test@mail.escuelaing.edu.co");
        e.setFullName("Test");
        e.setPassword("hashed");
        e.setVerified(true);
        e.setRole(Role.ESTUDIANTE);
        e.setStatus(UserStatus.ACTIVO);
        e.setCreatedAt(LocalDateTime.now());
        return e;
    }

    private User buildUser() {
        return User.builder().id(userId).email("test@mail.escuelaing.edu.co")
                .fullName("Test").password("hashed").verified(true)
                .role(Role.ESTUDIANTE).status(UserStatus.ACTIVO)
                .createdAt(LocalDateTime.now()).build();
    }

    @Test
    void findByEmail_existe_retornaUsuario() {
        when(jpaRepository.findByEmail(any())).thenReturn(Optional.of(buildEntity()));
        when(mapper.toDomain(any())).thenReturn(buildUser());
        assertTrue(adapter.findByEmail("test@mail.escuelaing.edu.co").isPresent());
    }

    @Test
    void findByEmail_noExiste_retornaVacio() {
        when(jpaRepository.findByEmail(any())).thenReturn(Optional.empty());
        assertTrue(adapter.findByEmail("no@mail.escuelaing.edu.co").isEmpty());
    }

    @Test
    void findById_existe_retornaUsuario() {
        when(jpaRepository.findById(userId)).thenReturn(Optional.of(buildEntity()));
        when(mapper.toDomain(any())).thenReturn(buildUser());
        assertTrue(adapter.findById(userId).isPresent());
    }

    @Test
    void findById_noExiste_retornaVacio() {
        when(jpaRepository.findById(any())).thenReturn(Optional.empty());
        assertTrue(adapter.findById(UUID.randomUUID()).isEmpty());
    }

    @Test
    void save_retornaUsuarioGuardado() {
        when(mapper.toEntity(any())).thenReturn(buildEntity());
        when(jpaRepository.save(any())).thenReturn(buildEntity());
        when(mapper.toDomain(any())).thenReturn(buildUser());
        User result = adapter.save(buildUser());
        assertNotNull(result);
    }

    @Test
    void existsByEmail_retornaTrue() {
        when(jpaRepository.existsByEmail("test@mail.escuelaing.edu.co")).thenReturn(true);
        assertTrue(adapter.existsByEmail("test@mail.escuelaing.edu.co"));
    }

    @Test
    void existsByEmailAndIdNot_retornaFalse() {
        when(jpaRepository.existsByEmailAndIdNot(any(), any())).thenReturn(false);
        assertFalse(adapter.existsByEmailAndIdNot("test@mail.escuelaing.edu.co", userId));
    }

    @Test
    void existsByUserNameAndIdNot_retornaFalse() {
        when(jpaRepository.existsByUserNameAndIdNot(any(), any())).thenReturn(false);
        assertFalse(adapter.existsByUserNameAndIdNot("username", userId));
    }

    @Test
    void findAll_retornaLista() {
        when(jpaRepository.findAll()).thenReturn(List.of(buildEntity()));
        when(mapper.toDomain(any())).thenReturn(buildUser());
        assertEquals(1, adapter.findAll().size());
    }

    @Test
    void findByFilters_retornaLista() {
        when(jpaRepository.findByFilters(any(), any(), any(), any())).thenReturn(List.of(buildEntity()));
        when(mapper.toDomain(any())).thenReturn(buildUser());
        assertEquals(1, adapter.findByFilters(null, null, null, null).size());
    }

    @Test
    void deleteById_llamaJpaRepository() {
        doNothing().when(jpaRepository).deleteById(userId);
        adapter.deleteById(userId);
        verify(jpaRepository).deleteById(userId);
    }
}
