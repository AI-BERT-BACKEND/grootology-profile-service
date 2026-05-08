package com.aibert.dosw.infrastructure.adapters.persistence.repository;

import com.aibert.dosw.domain.model.user.UserStatus;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE " +
           "(:name IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:status IS NULL OR u.status = :status)")
    List<UserEntity> findByFilters(@Param("name") String name,
                                   @Param("email") String email,
                                   @Param("status") UserStatus status);
}
