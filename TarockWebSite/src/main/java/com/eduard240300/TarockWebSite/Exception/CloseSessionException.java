package com.eduard240300.TarockWebSite.Exception;

public class CloseSessionException extends RuntimeException {
    public CloseSessionException(String errorMessage) {
        super(errorMessage);
    }
}
