package com.eduard240300.TarockWebSite.Exception;

public class LoginException extends RuntimeException {
    public LoginException(String errorMessage) {
        super(errorMessage);
    }
}
