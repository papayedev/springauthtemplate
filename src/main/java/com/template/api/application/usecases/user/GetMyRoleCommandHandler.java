package com.template.api.application.usecases.user;

import an.awesome.pipelinr.Command;
import com.template.api.domain.valueobject.Role;

public class GetMyRoleCommandHandler implements Command.Handler<GetMyRoleCommand, Role> {
    @Override
    public Role handle(GetMyRoleCommand command) {
        return command.userRole();
    }
}
