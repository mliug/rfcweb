package com.liug.rfcweb.entity;

public class RfcwebException extends RuntimeException {
    private int errCode;

    public RfcwebException(int err, String msg) {
        super(msg);
        errCode = err;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
