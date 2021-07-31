package com.Tarock.Common.Exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String errorMessage) {
        super(errorMessage);
    }
}
