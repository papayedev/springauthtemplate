package com.template.api.infrastructure.persistence.mapper;

import com.template.api.domain.model.User;
import com.template.api.domain.valueobject.Role;
import com.template.api.infrastructure.persistence.entity.UserEntity;

public class UserMapper {
    public static User toDomain(UserEntity entity) {
        return User.fromPersistence(
                entity.getId(),
                entity.getEmailAddress(),
                entity.getPasswordHash(),
                entity.getVerificationCode(),
                entity.getVerificationCodeExpirationDate(),
                entity.isActive(),
                entity.getRole()
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
        entity.setRole(user.isUser() ? Role.USER : Role.ADMIN);
        return entity;
    }
}
