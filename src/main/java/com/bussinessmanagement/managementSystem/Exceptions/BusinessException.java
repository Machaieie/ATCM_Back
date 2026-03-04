package com.bussinessmanagement.managementSystem.Exceptions;

public abstract class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
