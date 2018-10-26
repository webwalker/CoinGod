package com.webwalker.adapter.model;

import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.Utils;

/**
 * Created by xujian on 2018/7/7.
 */
public class BaseTradeItem {
    public TaskParams params;
    public TradeType type;
    public double price;
    public double amount;
    public String symbol;

    public BaseTradeItem(TradeType type, double price, double amount) {
        this.type = type;
        this.price = price;
        this.amount = amount;
    }

    public BaseTradeItem(TradeType type, double price, TaskParams params) {
        this.type = type;
        this.price = price;
        this.params = params;
        this.amount = params.amount;
        this.symbol = params.symbolValue;
    }

    public BaseTradeItem(TradeType type, double price, double amount, String symbol) {
        this.type = type;
        this.price = price;
        this.amount = amount;
        this.symbol = symbol;
    }

    public double getPrice() {
        return Utils.get4DotDouble(price);
    }
}
