package com.webwalker.adapter.http;

import java.util.Map;

/**
 * Created by xujian on 2017/3/24.
 */
public class APIStatus<T> {
    public int status;
    public String msg;
    public T result;
    public Map<String, Object> params;

    public APIStatus(int code) {
        this.status = code;
    }

    public APIStatus(int code, String msg) {
        this.status = code;
        this.msg = msg;
    }

    public APIStatus(int code, String msg, T result) {
        this.status = code;
        this.msg = msg;
        this.result = result;
    }

    public static APIStatus successInstance() {
        return new APIStatus(0, "");
    }

    public static APIStatus defaultInstance() {
        return new APIStatus(-1, "出错了");
    }
}
