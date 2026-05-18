package com.aibert.dosw.application.dto;

import com.aibert.dosw.application.dto.request.*;
import com.aibert.dosw.application.dto.response.AcademicProfileResponseDTO;
import com.aibert.dosw.application.dto.response.UserSummaryDTO;
import com.aibert.dosw.domain.model.user.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DtoConstructionTest {

    @Test
    void academicProfileDTO_getters() {
        AcademicProfileDTO dto = new AcademicProfileDTO();
        ReflectionTestUtils.setField(dto, "career", Career.INGENIERIA_SISTEMAS);
        ReflectionTestUtils.setField(dto, "doubleDegreeCareer", Career.INGENIERIA_INDUSTRIAL);
        ReflectionTestUtils.setField(dto, "currentSemester", 5);
        ReflectionTestUtils.setField(dto, "currentGpa", 3.8);
        ReflectionTestUtils.setField(dto, "currentSubjects", List.of("Algorithms"));
        ReflectionTestUtils.setField(dto, "academicGoal", AcademicGoal.APROBAR_TODO);
        ReflectionTestUtils.setField(dto, "currentlyWorking", false);
        ReflectionTestUtils.setField(dto, "availability", Availability.NOCHE);
        ReflectionTestUtils.setField(dto, "dailyStudyHours", 3);
        ReflectionTestUtils.setField(dto, "weeklyHours", 15);

        assertEquals(Career.INGENIERIA_SISTEMAS, dto.getCareer());
        assertEquals(Career.INGENIERIA_INDUSTRIAL, dto.getDoubleDegreeCareer());
        assertEquals(5, dto.getCurrentSemester());
        assertEquals(3.8, dto.getCurrentGpa());
        assertEquals(List.of("Algorithms"), dto.getCurrentSubjects());
        assertEquals(AcademicGoal.APROBAR_TODO, dto.getAcademicGoal());
        assertFalse(dto.getCurrentlyWorking());
        assertEquals(Availability.NOCHE, dto.getAvailability());
        assertEquals(3, dto.getDailyStudyHours());
        assertEquals(15, dto.getWeeklyHours());
    }

    @Test
    void adminEditUserRequestDTO_getters() {
        AdminEditUserRequestDTO dto = new AdminEditUserRequestDTO();
        ReflectionTestUtils.setField(dto, "fullName", "Nicolas Parrado");
        ReflectionTestUtils.setField(dto, "email", "n.parrado@mail.escuelaing.edu.co");

        assertEquals("Nicolas Parrado", dto.getFullName());
        assertEquals("n.parrado@mail.escuelaing.edu.co", dto.getEmail());
    }

    @Test
    void changeRoleRequestDTO_getters() {
        ChangeRoleRequestDTO dto = new ChangeRoleRequestDTO();
        ReflectionTestUtils.setField(dto, "newRole", Role.ADMIN);
        assertEquals(Role.ADMIN, dto.getNewRole());
    }

    @Test
    void passwordChangeDTO_getters() {
        PasswordChangeDTO dto = new PasswordChangeDTO();
        ReflectionTestUtils.setField(dto, "currentPassword", "OldPass1");
        ReflectionTestUtils.setField(dto, "newPassword", "NewPass1");
        ReflectionTestUtils.setField(dto, "confirmNewPassword", "NewPass1");

        assertEquals("OldPass1", dto.getCurrentPassword());
        assertEquals("NewPass1", dto.getNewPassword());
        assertEquals("NewPass1", dto.getConfirmNewPassword());
    }

    @Test
    void registerRequestDTO_getters() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        ReflectionTestUtils.setField(dto, "fullName", "Test User");
        ReflectionTestUtils.setField(dto, "email", "test@mail.escuelaing.edu.co");
        ReflectionTestUtils.setField(dto, "career", Career.INGENIERIA_SISTEMAS);
        ReflectionTestUtils.setField(dto, "password", "Pass1234");
        ReflectionTestUtils.setField(dto, "confirmPassword", "Pass1234");

        assertEquals("Test User", dto.getFullName());
        assertEquals("test@mail.escuelaing.edu.co", dto.getEmail());
        assertEquals(Career.INGENIERIA_SISTEMAS, dto.getCareer());
        assertEquals("Pass1234", dto.getPassword());
        assertEquals("Pass1234", dto.getConfirmPassword());
    }

    @Test
    void updateProfileDTO_getters() {
        UpdateProfileDTO dto = new UpdateProfileDTO();
        ReflectionTestUtils.setField(dto, "userName", "nicolas.parrado");
        assertEquals("nicolas.parrado", dto.getUserName());
    }

    @Test
    void updateProfileDTO_noArgsConstructor() {
        UpdateProfileDTO dto = new UpdateProfileDTO();
        assertNull(dto.getUserName());
    }

    @Test
    void userSummaryDTO_builder() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        UserSummaryDTO dto = UserSummaryDTO.builder()
                .id(id)
                .fullName("Test")
                .email("test@mail.escuelaing.edu.co")
                .role(Role.ESTUDIANTE)
                .status(UserStatus.ACTIVO)
                .createdAt(now)
                .build();

        assertEquals(id, dto.getId());
        assertEquals("Test", dto.getFullName());
        assertEquals("test@mail.escuelaing.edu.co", dto.getEmail());
        assertEquals(Role.ESTUDIANTE, dto.getRole());
        assertEquals(UserStatus.ACTIVO, dto.getStatus());
        assertEquals(now, dto.getCreatedAt());
    }

    @Test
    void academicProfileResponseDTO_builder() {
        AcademicProfileResponseDTO dto = AcademicProfileResponseDTO.builder()
                .career("INGENIERIA_SISTEMAS")
                .doubleDegreeCareer("INGENIERIA_INDUSTRIAL")
                .currentSemester(5)
                .weeklyHours(15)
                .dailyStudyHours(3)
                .currentGpa(3.8)
                .currentSubjects(List.of("Algorithms"))
                .academicGoal(AcademicGoal.APROBAR_TODO)
                .currentlyWorking(false)
                .availability(Availability.NOCHE)
                .profileComplete(true)
                .build();

        assertEquals("INGENIERIA_SISTEMAS", dto.getCareer());
        assertEquals("INGENIERIA_INDUSTRIAL", dto.getDoubleDegreeCareer());
        assertEquals(5, dto.getCurrentSemester());
        assertEquals(15, dto.getWeeklyHours());
        assertEquals(3, dto.getDailyStudyHours());
        assertEquals(3.8, dto.getCurrentGpa());
        assertEquals(List.of("Algorithms"), dto.getCurrentSubjects());
        assertEquals(AcademicGoal.APROBAR_TODO, dto.getAcademicGoal());
        assertFalse(dto.getCurrentlyWorking());
        assertEquals(Availability.NOCHE, dto.getAvailability());
        assertTrue(dto.isProfileComplete());
    }
}
