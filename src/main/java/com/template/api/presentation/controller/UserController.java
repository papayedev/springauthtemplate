package com.template.api.presentation.controller;

import an.awesome.pipelinr.Pipeline;
import com.template.api.application.ports.AuthContext;
import com.template.api.application.usecases.user.GetMyRoleCommand;
import com.template.api.domain.exceptions.UnauthorizedException;
import com.template.api.domain.valueobject.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Pipeline pipeline;
    private final AuthContext authContext;

    public  UserController(Pipeline pipeline, AuthContext authContext) {
        this.pipeline = pipeline;
        this.authContext = authContext;
    }


    @GetMapping("/role")
    public ResponseEntity<Role> getMyRole() {
        var user = authContext.getAuthUser()
                .orElseThrow(UnauthorizedException::new);
        return ResponseEntity.ok(this.pipeline.send(new GetMyRoleCommand(Role.valueOf(user.getRole()))));
    }
}
