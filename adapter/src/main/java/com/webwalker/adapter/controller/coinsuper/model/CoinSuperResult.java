package com.webwalker.adapter.controller.coinsuper.model;

import com.webwalker.adapter.model.CommonResult;

/**
 * Created by xujian on 2018/7/16.
 */
public class CoinSuperResult<T> extends CommonResult<T> {
    @Override
    public int code() {
        return super.code();
    }

    @Override
    public String message() {
        return super.message();
    }

    @Override
    public boolean success() {
        return code == 1000;
    }
}
