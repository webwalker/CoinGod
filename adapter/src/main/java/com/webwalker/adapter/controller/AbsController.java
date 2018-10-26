package com.webwalker.adapter.controller;

import com.webwalker.adapter.model.BaseCancelItem;
import com.webwalker.adapter.model.BaseDepthItem;
import com.webwalker.adapter.model.BaseOrderItem;
import com.webwalker.adapter.model.BaseMarketItem;
import com.webwalker.adapter.model.BaseTradeItem;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.Logger;

import java.util.Map;

/**
 * Created by xujian on 2018/7/7.
 */
public abstract class AbsController {
    protected TaskParams params;
    protected String key;

    public AbsController(TaskParams params) {
        setParams(params);
    }

    public void setParams(TaskParams params) {
        if (params != null) {
            this.params = params;
            this.key = params.accessKey;
        }
    }

    private long startTime;

    public abstract void symbol(ICallback callback);

    //深度
    public abstract void depth(BaseDepthItem depth, ICallback callback);

    //行情
    public abstract void market(BaseMarketItem market, ICallback callback);

    //交易
    public void trade(BaseTradeItem trade) {
        startTime = System.currentTimeMillis();
    }

    //委托的订单列表
    public abstract void order(BaseOrderItem detail, final ICallback callback);

    //取消订单委托
    public abstract void cancel(BaseCancelItem cancel);

    protected abstract String getUrl(String url);

    protected abstract Map<String, String> buildHeaders(Map<String, String> headers);

    protected abstract Map<String, Object> buildParams(Map<String, Object> params);

    protected abstract String sign(Map<String, Object> params);

    protected double getTradeTime() {
        return (System.currentTimeMillis() - startTime) * 1.000 / 1000;
    }

    protected void log(String log) {
        Logger.d(key, log);
    }
}
