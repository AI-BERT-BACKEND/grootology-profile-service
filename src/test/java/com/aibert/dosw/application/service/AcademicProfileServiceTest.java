package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.AcademicProfileDTO;
import com.aibert.dosw.application.dto.response.AcademicProfileResponseDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.*;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
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
class AcademicProfileServiceTest {

    @Mock private UserRepositoryPort userRepository;
    @InjectMocks private AcademicProfileService academicProfileService;

    private final UUID userId = UUID.randomUUID();

    private User buildUser() {
        return User.builder()
                .id(userId).fullName("Test User")
                .email("test@mail.escuelaing.edu.co").password("hashed")
                .verified(true).role(Role.ESTUDIANTE).status(UserStatus.ACTIVO)
                .career(Career.INGENIERIA_SISTEMAS).currentSemester(3)
                .profileComplete(false).createdAt(LocalDateTime.now()).build();
    }

    private AcademicProfileDTO buildDto() {
        AcademicProfileDTO dto = mock(AcademicProfileDTO.class);
        when(dto.getSecondaryCareer()).thenReturn(null);
        when(dto.getCurrentSemester()).thenReturn(3);
        when(dto.getDailyStudyHours()).thenReturn(4);
        when(dto.getCurrentGpa()).thenReturn(3.8);
        when(dto.getSubjects()).thenReturn(List.of("Cálculo", "Álgebra", "Física"));
        when(dto.getAcademicGoal()).thenReturn(AcademicGoal.EXCELENCIA);
        when(dto.getCurrentlyEmployed()).thenReturn(false);
        when(dto.getStudyAvailability()).thenReturn(Availability.TARDE);
        return dto;
    }

    @Test
    void saveAcademicProfile_dobleCarreraIgualPrincipal_lanzaException() {
        User user = buildUser();
        AcademicProfileDTO dto = mock(AcademicProfileDTO.class);
        when(dto.getSecondaryCareer()).thenReturn(Career.INGENIERIA_SISTEMAS);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(IllegalArgumentException.class,
                () -> academicProfileService.saveAcademicProfile(userId, dto));
    }

    @Test
    void saveAcademicProfile_exitoso_retornaDatos() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        AcademicProfileResponseDTO response = academicProfileService.saveAcademicProfile(userId, buildDto());

        assertNotNull(response);
        assertEquals(28, response.getWeeklyHours()); // 4 * 7 = 28
        assertEquals(3.8, response.getCurrentGpa());
        assertEquals(4, response.getDailyStudyHours());
        assertEquals(Availability.TARDE, response.getAvailability());
        assertFalse(response.getCurrentSubjects().isEmpty());
        assertTrue(response.isProfileComplete());
    }

    @Test
    void saveAcademicProfile_conDobleCarrera_retornaDatos() {
        AcademicProfileDTO dto = buildDto();
        when(dto.getSecondaryCareer()).thenReturn(Career.MATEMATICAS);
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        AcademicProfileResponseDTO response = academicProfileService.saveAcademicProfile(userId, dto);
        assertEquals("MATEMATICAS", response.getDoubleDegreeCareer());
    }

    @Test
    void saveAcademicProfile_conObjetivoEquilibrio_retornaDatos() {
        AcademicProfileDTO dto = buildDto();
        when(dto.getAcademicGoal()).thenReturn(AcademicGoal.EQUILIBRIO);
        when(userRepository.findById(userId)).thenReturn(Optional.of(buildUser()));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        AcademicProfileResponseDTO response = academicProfileService.saveAcademicProfile(userId, dto);
        assertEquals(AcademicGoal.EQUILIBRIO, response.getAcademicGoal());
    }

    @Test
    void saveAcademicProfile_usuarioNoExiste_lanzaException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> academicProfileService.saveAcademicProfile(userId, mock(AcademicProfileDTO.class)));
    }
}
