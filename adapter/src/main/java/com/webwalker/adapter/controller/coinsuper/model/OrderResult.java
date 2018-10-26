package com.webwalker.adapter.controller.coinsuper.model;

import java.util.List;

/**
 * Created by xujian on 2018/7/7.
 */
public class OrderResult extends CoinSuperResult<OrderResult.OrderItem> {
    public static class OrderItem{
        public long timestamp;
        public List<String> result;
    }
}
