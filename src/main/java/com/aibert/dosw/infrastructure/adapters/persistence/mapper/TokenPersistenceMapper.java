package com.aibert.dosw.infrastructure.adapters.persistence.mapper;

import com.aibert.dosw.domain.model.user.EmailVerificationToken;
import com.aibert.dosw.infrastructure.adapters.persistence.entity.EmailVerificationTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenPersistenceMapper {
    EmailVerificationToken toDomain(EmailVerificationTokenEntity entity);
    EmailVerificationTokenEntity toEntity(EmailVerificationToken token);
}
