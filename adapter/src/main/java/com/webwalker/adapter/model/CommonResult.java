package com.webwalker.adapter.model;

/**
 * Created by xujian on 2018/7/7.
 */
public class CommonResult<T> extends BaseResult<T> {
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return msg;
    }

    @Override
    public boolean success() {
        return code == 0;
    }
}
