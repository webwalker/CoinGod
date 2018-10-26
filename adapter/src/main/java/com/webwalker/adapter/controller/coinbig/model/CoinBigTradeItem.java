package com.webwalker.adapter.controller.coinbig.model;

import com.webwalker.adapter.model.BaseTradeItem;
import com.webwalker.adapter.model.TradeType;
import com.webwalker.core.config.TaskParams;

/**
 * Created by xujian on 2018/7/7.
 */
public class CoinBigTradeItem extends BaseTradeItem {
    public CoinBigTradeItem(TradeType type, double price, TaskParams params) {
        super(type, price, params);
    }

    public CoinBigTradeItem(TradeType type, double price, double amount, String symbol) {
        super(type, price, amount, symbol);
    }

    public String getType() {
        switch (type) {
            case Buy:
                return "buy";
            case Sell:
                return "sell";
            case Buy_Market:
                return "buy_market";
            case Sell_Market:
                return "sell_market";
        }
        return "";
    }
}
