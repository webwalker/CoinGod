package com.webwalker.adapter.http;

/**
 * Created by xujian on 2017/3/24.
 */
public interface IApiCallback<T> {
    void onSuccess(T result);

    void onFailed(APIStatus<T> status);

    void onResponse(T result);
}