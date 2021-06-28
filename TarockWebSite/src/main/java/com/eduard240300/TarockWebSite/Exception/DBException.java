package com.eduard240300.TarockWebSite.Exception;

public class DBException extends RuntimeException {
    public DBException(String errorMessage) {
        super(errorMessage);
    }
}
