package com.template.api.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.template.api.auth.domain.exceptions.UnauthorizedException;
import com.template.api.auth.application.ports.UserRepository;
import com.template.api.auth.application.services.PasswordHasher;
import com.template.api.auth.domain.valueobject.Password;
import com.template.api.auth.domain.viewmodel.VoidResponse;
import com.template.api.core.application.ports.APILogger;
import com.template.api.auth.domain.exceptions.ForbiddenException;
import com.template.api.auth.domain.exceptions.NotFoundException;

import java.util.Objects;

public class UpdatePasswordCommandHandler implements Command.Handler<UpdatePasswordCommand, VoidResponse> {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final APILogger apiLogger;

    public UpdatePasswordCommandHandler(UserRepository userRepository, PasswordHasher passwordHasher, APILogger apiLogger) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.apiLogger = apiLogger;
    }

    @Override
    public VoidResponse handle(UpdatePasswordCommand updatePasswordCommand) {
        apiLogger.info("Update Password Request");

        final var user = userRepository.findByEmailAddress(updatePasswordCommand.getEmail())
                .orElseThrow(() -> new NotFoundException("User"));

        if (!user.isActive()) {
            throw new ForbiddenException("User is not active");
        }

        if (user.getVerificationCode() == null) {
            throw new UnauthorizedException();
        }

        if (user.isVerificationCodeExpired()) {
            throw new UnauthorizedException();
        }

        var password = new Password(updatePasswordCommand.getPassword(), null);

        if (Objects.equals(user.getVerificationCode(), updatePasswordCommand.getVerificationCode())) {
            user.resetPassword(passwordHasher.hash(password.getClearPassword()));
            userRepository.save(user);
            apiLogger.info("Password updated");
        } else {
            throw new UnauthorizedException();
        }

        return new VoidResponse();
    }
}
