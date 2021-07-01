package com.eduard240300.TarockWebSite.Exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String errorMessage) {
        super(errorMessage);
    }
}
