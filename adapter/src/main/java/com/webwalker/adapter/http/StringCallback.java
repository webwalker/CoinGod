package com.webwalker.adapter.http;

import com.webwalker.http.callback.Callback;

import okhttp3.Response;

/**
 * Created by xujian on 2018/7/7.
 */
public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().string();
    }
}
