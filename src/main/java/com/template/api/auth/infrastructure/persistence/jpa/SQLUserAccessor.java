package com.template.api.auth.infrastructure.persistence.jpa;

import com.template.api.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface SQLUserAccessor extends CrudRepository<UserEntity, String> {
    UserEntity findByEmailAddress(String emailAddress);
} 