package com.eduard240300.TarockWebSite.Exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String errorMessage) {
        super(errorMessage);
    }
}
