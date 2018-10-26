package com.webwalker.adapter.controller.coinbig.model;

import com.webwalker.adapter.model.CommonResult;

/**
 * Created by xujian on 2018/7/7.
 */
public class TradeResult extends CommonResult<TradeResult.TradeItem> {
    public static class TradeItem {
        public boolean result;
        public long order_id;
    }
}
