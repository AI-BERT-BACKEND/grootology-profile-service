package com.aibert.dosw.domain.ports.in;

import com.aibert.dosw.application.dto.request.AdminEditUserRequestDTO;
import com.aibert.dosw.application.dto.request.ChangeRoleRequestDTO;
import com.aibert.dosw.application.dto.response.UserSummaryDTO;

import java.util.List;
import java.util.UUID;

public interface AdminUserUseCase {
    List<UserSummaryDTO> listUsers(String name, String email, String status, String role);
    UserSummaryDTO getUserDetail(UUID userId);
    UserSummaryDTO editUser(UUID adminId, UUID userId, AdminEditUserRequestDTO request);
    UserSummaryDTO updateUserStatus(UUID adminId, UUID userId, String newStatus);
    void deleteUser(UUID adminId, UUID userId);
    UserSummaryDTO changeRole(UUID adminId, UUID userId, ChangeRoleRequestDTO request);
}
