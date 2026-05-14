package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.core.application.ports.Mailer;
import com.template.api.application.ports.UserRepository;
import com.template.api.domain.viewmodel.VoidResponse;
import com.template.api.core.application.ports.APILogger;
import com.template.api.domain.exceptions.ForbiddenException;
import com.template.api.domain.exceptions.NotFoundException;

public class ResetPasswordRequestCommandHandler implements Command.Handler<ResetPasswordRequestCommand, VoidResponse> {
    private final UserRepository userRepository;
    private final Mailer mailer;
    private final APILogger apiLogger;

    public ResetPasswordRequestCommandHandler(UserRepository userRepository,
                                              Mailer mailer,
                                              APILogger apiLogger) {
        this.userRepository = userRepository;
        this.mailer = mailer;
        this.apiLogger = apiLogger;
    }

    @Override
    public VoidResponse handle(ResetPasswordRequestCommand resetPasswordRequestCommand) {
        apiLogger.info("Reset Password Request");
        final String email = resetPasswordRequestCommand.getEmail();

        final var maybeUser = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new NotFoundException("User"));

        if (!maybeUser.isActive()) {
            throw new ForbiddenException("User is not active");
        }

        maybeUser.createVerificationCode(15);

        userRepository.save(maybeUser);

        mailer.sendVerificationCode(maybeUser.getEmailAddress(), maybeUser.getVerificationCode());

        apiLogger.info("Reset request sent");

        return new VoidResponse();
    }
}
