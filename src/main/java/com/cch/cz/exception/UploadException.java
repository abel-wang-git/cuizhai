package com.cch.cz.exception;

public class UploadException  extends Exception{
    private String message;

    public UploadException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
