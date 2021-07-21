package com.Tarock.Server.Exception;

public class LoginException extends RuntimeException {
    public LoginException(String errorMessage) {
        super(errorMessage);
    }
}
