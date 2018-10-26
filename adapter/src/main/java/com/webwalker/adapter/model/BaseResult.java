package com.webwalker.adapter.model;

import com.webwalker.core.utility.JsonUtil;

/**
 * Created by xujian on 2017/11/20.
 */
public abstract class BaseResult<T> implements java.io.Serializable {
    public int code;
    public String msg;
    public T data;

    public abstract int code();

    public abstract String message();

    public abstract boolean success();

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
