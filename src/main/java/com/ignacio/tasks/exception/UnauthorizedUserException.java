package com.ignacio.tasks.exception;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException(String message){
        super(message);
    }
}

