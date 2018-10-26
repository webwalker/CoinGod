package com.webwalker.http.request;

import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.utility.JsonUtil;
import com.webwalker.core.utility.Logger;
import com.webwalker.http.builder.PostFormBuilder;
import com.webwalker.http.builder.PostJsonBuilder;
import com.webwalker.http.callback.Callback;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/12/14.
 */
public class PostJsonRequest extends OkHttpRequest {
    private List<PostJsonBuilder.FileInput> files;
    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public PostJsonRequest(String url, Object tag, Map<String, Object> params, Map<String, String> headers, List<PostJsonBuilder.FileInput> files, int id) {
        super(url, tag, params, headers, id);
        this.files = files;
    }

    @Override
    protected RequestBody buildRequestBody() {
        String json = JsonUtil.toJson(params);
        if (ConfigResolver.debug) {
            Logger.dn(json);
        }
        return RequestBody.create(JSON, JsonUtil.toJson(params));
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void addParams(MultipartBody.Builder builder) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, getValue(key)));
            }
        }
    }

    private void addParams(FormBody.Builder builder) {
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, getValue(key));
            }
        }
    }

}
