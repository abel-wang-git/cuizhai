package com.cch.cz.base.exception;

@SuppressWarnings("serial")
public class CZException extends RuntimeException {

    public CZException() {
        super();
    }

    public CZException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public CZException(String arg0) {
        super(arg0);
    }

    public CZException(Throwable arg0) {
        super(arg0);
    }

}
