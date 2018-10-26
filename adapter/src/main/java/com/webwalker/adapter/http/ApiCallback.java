package com.webwalker.adapter.http;

import com.webwalker.adapter.model.BaseResult;
import com.webwalker.http.callback.Callback;

/**
 * Created by xujian on 2017/3/24.
 */
public abstract class ApiCallback<T> extends Callback<T> implements IApiCallback<T> {
    protected final static int DEFAULT_CODE = 400;

    @Override
    public void onResponse(T result) {
        if (null == result) {
            onFailed(new APIStatus<T>(DEFAULT_CODE, "network error"));
        } else if (result instanceof BaseResult) {
            BaseResult baseResult = (BaseResult) result;
            if (baseResult.success()) {
                onSuccess(result);
            } else {
                onFailed(new APIStatus<>(baseResult.code(), baseResult.message(), result));
            }
        }
    }
}
