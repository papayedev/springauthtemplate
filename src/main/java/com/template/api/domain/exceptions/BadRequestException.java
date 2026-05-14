package com.template.api.domain.exceptions;

public class BadRequestException extends IllegalArgumentException{
    public BadRequestException(String message) {
        super(message);
    }
}
