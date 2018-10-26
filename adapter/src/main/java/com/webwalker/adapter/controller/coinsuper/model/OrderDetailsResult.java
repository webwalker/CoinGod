package com.webwalker.adapter.controller.coinsuper.model;

import java.util.List;

/**
 * Created by xujian on 2018/7/16.
 */
public class OrderDetailsResult extends CoinSuperResult<OrderDetailsResult.OrderDetailItem> {
    public static class OrderDetailItem{
        public long timestamp;
        public List<ResultBean> result;

        public static class ResultBean {
            public int orderNo;
            public String action;
            public String orderType;
            public double priceLimit;
            public String symbol;
            public double quantity;
            public long quantityRemaining;
            public String amount;
            public String amountRemaining;
            public String fee;
            public long utcUpdate;
            public long utcCreate;
            public String state;
        }
    }
}
