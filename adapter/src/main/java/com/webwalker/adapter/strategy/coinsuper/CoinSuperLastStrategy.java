package com.webwalker.adapter.strategy.coinsuper;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.adapter.controller.coinbig.CoinBigController;
import com.webwalker.adapter.controller.coinbig.model.CoinBigTradeItem;
import com.webwalker.adapter.controller.coinsuper.model.MarketResult;
import com.webwalker.adapter.model.BaseMarketItem;
import com.webwalker.adapter.model.TradeType;
import com.webwalker.adapter.strategy.CoinBaseStrategy;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.TimeUtil;

import java.util.TimerTask;

/**
 * Created by xujian on 2018/7/8.
 */
public class CoinSuperLastStrategy extends CoinSuperBaseStrategy {
    public CoinSuperLastStrategy(TaskParams params) {
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