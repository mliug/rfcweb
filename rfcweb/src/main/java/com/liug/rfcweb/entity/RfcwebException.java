package com.liug.rfcweb.entity;

public class RfcwebException extends RuntimeException {
    public RfcwebException( ) {
        int errCode;
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
