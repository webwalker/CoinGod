package com.webwalker.adapter.controller.coinsuper.model;

import com.webwalker.adapter.model.BaseTradeItem;
import com.webwalker.adapter.model.TradeType;
import com.webwalker.core.config.TaskParams;

/**
 * Created by xujian on 2018/7/7.
 */
public class CoinSuperTradeItem extends BaseTradeItem {
    public CoinSuperTradeItem(TradeType type, double price, TaskParams params) {
        super(type, price, params);
    }

    public CoinSuperTradeItem(TradeType type, double price, double amount, String symbol) {
        super(type, price, amount, symbol);
    }

    public String getType() {
        switch (type) {
            case Buy:
                return "LMT";
            case Sell:
                return "LMT";
            case Buy_Market:
                return "MKT";
            case Sell_Market:
                return "MKT";
        }
        return "";
    }
}
