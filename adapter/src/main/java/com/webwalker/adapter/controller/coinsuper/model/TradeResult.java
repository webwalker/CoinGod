package com.webwalker.adapter.controller.coinsuper.model;

/**
 * Created by xujian on 2018/7/7.
 */
public class TradeResult extends CoinSuperResult<TradeResult.TradeItem> {
    public static class TradeItem {
        public long timestamp;
        public ResultBean result;

        public static class ResultBean {
            public String orderNo;
        }
    }
}
