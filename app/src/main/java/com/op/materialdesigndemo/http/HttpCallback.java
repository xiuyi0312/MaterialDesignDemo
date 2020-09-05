package com.op.materialdesigndemo.http;

public interface HttpCallback<T> {
    void onSuccess(T result);
    void onError(ApiException e);
}
