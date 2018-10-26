package com.webwalker.http.builder;

import com.webwalker.http.OkHttpUtils;
import com.webwalker.http.request.OtherRequest;
import com.webwalker.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
