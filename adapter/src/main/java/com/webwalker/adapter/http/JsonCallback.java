package com.webwalker.adapter.http;

import com.webwalker.adapter.model.BaseResult;
import com.webwalker.core.utility.JsonUtil;
import com.webwalker.core.utility.Logger;
import com.webwalker.core.config.ConfigResolver;

import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xujian on 2018/7/7.
 */
public abstract class JsonCallback<T extends BaseResult> extends ApiCallback<T>  {
    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T bean = JsonUtil.fromJson(string, entityClass);

        if (ConfigResolver.debug) {
            Logger.dn(string);
        }

        onResponse(bean);
        return bean;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        onFailed(new APIStatus<T>(DEFAULT_CODE, e.getMessage()));
    }

    @Override
    public void onResponse(T response, int id) {
    }
}
