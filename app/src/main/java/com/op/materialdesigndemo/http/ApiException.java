package com.op.materialdesigndemo.http;

public class ApiException extends Exception {
    private int code;

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
