package com.myapps.exception;

public class ExecutionNotFoundException extends RuntimeException {
    public ExecutionNotFoundException(String message    ) {
        super(message);
    }
}
