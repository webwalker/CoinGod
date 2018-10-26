package com.webwalker.adapter.strategy.coinsuper;

import com.webwalker.adapter.controller.coinbig.model.CoinBigTradeItem;
import com.webwalker.adapter.controller.coinsuper.model.MarketResult;
import com.webwalker.adapter.model.BaseMarketItem;
import com.webwalker.adapter.model.TradeType;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.TimeUtil;
import com.webwalker.core.utility.Utils;

import java.util.TimerTask;

/**
 * Created by xujian on 2018/7/8.
 */
public class CoinSuperMidStrategy extends CoinSuperBaseStrategy {
    public CoinSuperMidStrategy(TaskParams params) {
        super(params);
    }

    @Override
    public void start() {
        controller.market(new BaseMarketItem(params.accessKey, params.symbolValue),
                new ICallback<MarketResult>() {
                    @Override
                    public void action(MarketResult result) {

                    }
                });
    }
}