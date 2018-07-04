package com.cch.cz.exception;

public class ObjectNullException extends Exception {
    private String message;

    public ObjectNullException() {
        super();
    }

    public ObjectNullException(String message) {
        super();
        this.message = message;
    }
}
