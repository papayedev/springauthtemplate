package com.template.api.application.usecases.user;

import an.awesome.pipelinr.Command;
import com.template.api.domain.valueobject.Role;

public record GetMyRoleCommand(
        Role userRole
) implements Command<Role> {
}
