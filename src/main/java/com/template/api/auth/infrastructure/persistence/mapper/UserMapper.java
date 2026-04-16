package com.template.api.auth.infrastructure.persistence.mapper;

import com.template.api.auth.domain.model.User;
import com.template.api.auth.infrastructure.persistence.entity.UserEntity;

public class UserMapper {
    public static User toDomain(UserEntity entity) {
        return User.fromPersistence(
                entity.getId(),
                entity.getEmailAddress(),
                entity.getPasswordHash(),
                entity.getVerificationCode(),
                entity.getVerificationCodeExpirationDate(),
                entity.isActive()
        );
    }

    public static UserEntity toEntity(User user) {
        var entity = new UserEntity();
        entity.setId(user.getId());
        entity.setVerificationCodeExpirationDate(user.getVerificationCodeExpirationDate());
        entity.setVerificationCode(user.getVerificationCode());
        entity.setActive(user.isActive());
        entity.setEmailAddress(user.getEmailAddress());
        entity.setPasswordHash(user.getPassword());
        return entity;
    }
}
