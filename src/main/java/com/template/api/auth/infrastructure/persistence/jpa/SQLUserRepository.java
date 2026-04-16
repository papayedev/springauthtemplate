package com.template.api.auth.infrastructure.persistence.jpa;

import com.template.api.auth.application.ports.UserRepository;
import com.template.api.auth.domain.model.User;
import com.template.api.auth.infrastructure.persistence.entity.UserEntity;
import com.template.api.auth.infrastructure.persistence.mapper.UserMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.Optional;

public class SQLUserRepository implements UserRepository {

    private final SQLUserAccessor accessor;

    public SQLUserRepository(SQLUserAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public Optional<User> findById(String id) {
        return accessor.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        return Optional.ofNullable(accessor.findByEmailAddress(emailAddress))
                .map(UserMapper::toDomain);
    }

    @Override
    public void save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        accessor.save(entity);
    }

    @Override
    public void clear() {
        accessor.deleteAll();
    }
}