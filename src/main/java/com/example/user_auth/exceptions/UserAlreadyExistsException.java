package com.example.user_auth.exceptions;

public class UserAlreadyExistsException extends InvalidRequestException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
