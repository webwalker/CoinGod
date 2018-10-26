package com.webwalker.adapter.controller.coinsuper.model;

import com.webwalker.adapter.model.CommonResult;

import java.util.List;

/**
 * Created by xujian on 2018/7/8.
 */
public class MarketResult extends CoinSuperResult<MarketResult.MarketItem> {
    public static class MarketItem {
        public long timestamp;
        public List<ResultBean> result;

        public static class ResultBean {
            public String price;
            public String tradeType;
            public String volume;
            public long timestamp;
        }
    }
}
