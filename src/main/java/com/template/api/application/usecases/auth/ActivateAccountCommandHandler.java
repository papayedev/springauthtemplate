package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.application.ports.UserRepository;
import com.template.api.domain.exceptions.UnauthorizedException;
import com.template.api.domain.viewmodel.IdResponse;
import com.template.api.core.application.ports.APILogger;
import com.template.api.domain.exceptions.BadRequestException;
import com.template.api.domain.exceptions.ForbiddenException;
import com.template.api.domain.exceptions.NotFoundException;

public class ActivateAccountCommandHandler implements Command.Handler<ActivateAccountCommand, IdResponse> {
    private final UserRepository userRepository;
    private final APILogger apiLogger;

    public ActivateAccountCommandHandler(UserRepository userRepository, APILogger apiLogger) {
        this.userRepository = userRepository;
        this.apiLogger = apiLogger;
    }

    @Override
    public IdResponse handle(ActivateAccountCommand command) {
        apiLogger.info("Active account command received");
        var user = userRepository.findByEmailAddress(command.getEmail())
                .orElseThrow(() -> new NotFoundException("User"));

        if (user.isActive()) {
            throw new ForbiddenException("User is already active");
        }

        if (user.isVerificationCodeExpired()) {
            throw new UnauthorizedException("Verification code is expired");
        }

        var match = command.getVerificationCode().equals(user.getVerificationCode());

        if (!match) {
            throw new BadRequestException("Verification code is wrong");
        }

        user.makeActive();
        user.resetVerificationCode();

        userRepository.save(user);

        apiLogger.info("Active account has been activated");

        return new IdResponse(user.getId());
    }
}
