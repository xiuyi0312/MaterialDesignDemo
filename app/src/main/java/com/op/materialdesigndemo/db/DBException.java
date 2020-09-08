package com.op.materialdesigndemo.db;

public class DBException extends Exception {
    private int code;

    public DBException(String message, int code) {
        super(message);
        this.code = code;
    }
}
