package com.TarockServer.Exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String errorMessage) {
        super(errorMessage);
    }
}
