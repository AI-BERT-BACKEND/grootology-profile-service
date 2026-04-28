package com.aibert.dosw.domain.ports.out;

import com.aibert.dosw.domain.model.user.User;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User save(User user);
    boolean existsByEmail(String email);
}
