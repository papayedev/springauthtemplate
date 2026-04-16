package com.template.api.auth.domain.exceptions;

public class BadRequestException extends IllegalArgumentException{
    public BadRequestException(String message) {
        super(message);
    }
}
