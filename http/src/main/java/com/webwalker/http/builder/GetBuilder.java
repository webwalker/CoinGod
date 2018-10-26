package com.webwalker.http.builder;

import com.webwalker.core.utility.Utils;
import com.webwalker.http.request.GetRequest;
import com.webwalker.http.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhy on 15/12/14.
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable {
    @Override
    public RequestCall build() {
        if (params != null) {
            url = Utils.appendQueryParameter(url, params);
        }
        return new GetRequest(url, tag, params, headers, id).build();
    }

    @Override
    public GetBuilder params(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }


}
