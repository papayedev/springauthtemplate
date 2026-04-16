package com.template.api.auth.presentation.controller;

import an.awesome.pipelinr.Pipeline;
import com.template.api.auth.application.usecases.*;
import com.template.api.auth.presentation.dto.*;
import com.template.api.auth.domain.viewmodel.AccessTokenViewModel;
import com.template.api.auth.domain.viewmodel.LoggedInUserViewModel;
import com.template.api.auth.domain.viewmodel.IdResponse;
import com.template.api.auth.domain.viewmodel.VoidResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Transactional
public class AuthController {
    private final Pipeline pipeline;

    public AuthController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping("/register")
    public ResponseEntity<IdResponse> register(
            @Valid @RequestBody RegisterDTO dto
    ) {
        return new ResponseEntity<>(pipeline.send(new RegisterCommand(
                dto.getEmail(),
                dto.getPassword()
        )), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedInUserViewModel> login(
            @Valid @RequestBody LoginDTO dto
    ) {
        return ResponseEntity.ok(pipeline.send(new LoginCommand(
                dto.getEmail(),
                dto.getPassword()
        )));
    }

    @PostMapping("/activate")
    public ResponseEntity<IdResponse> activateAccount(
            @Valid @RequestBody ActiveAccountDTO dto
    ) {
        return ResponseEntity.ok(pipeline.send(new ActivateAccountCommand(
                dto.getEmail(),
                dto.getVerificationCode()
        )));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenViewModel> refreshToken(
            @Valid @RequestBody RefreshTokenDTO dto
    ) {
        return ResponseEntity.ok(pipeline.send(new RefreshTokenCommand(
                dto.getRefreshToken()
        )));
    }

    @PostMapping("/request/password")
    public ResponseEntity<VoidResponse>  resetPasswordRequest(
            @Valid @RequestBody ResetPasswordRequestDTO dto
    ) {
        return ResponseEntity.ok(pipeline.send(new ResetPasswordRequestCommand(
                dto.getEmail()
        )));
    }

    @PutMapping("/update/password")
    public ResponseEntity<VoidResponse> updatePassword(
            @Valid @RequestBody UpdatePasswordDTO dto
    ) {
        return ResponseEntity.ok(pipeline.send(new UpdatePasswordCommand(
                dto.getEmail(),
                dto.getVerificationCode(),
                dto.getPassword()
        )));
    }
}
