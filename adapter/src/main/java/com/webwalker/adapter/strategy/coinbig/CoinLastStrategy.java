package com.webwalker.adapter.strategy.coinbig;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.adapter.controller.coinbig.CoinBigController;
import com.webwalker.adapter.controller.coinbig.model.CoinBigTradeItem;
import com.webwalker.adapter.controller.coinbig.model.MarketResult;
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
public class CoinLastStrategy extends CoinBaseStrategy {
    private CoinBigController controller;

    public CoinLastStrategy(TaskParams params) {
        super(params);
        controller = new CoinBigController(params);
    }

    @Override
    protected AbsController getController() {
        return controller;
    }

    @Override
    public void start() {
        controller.market(new BaseMarketItem(params.accessKey, params.symbolValue),
                new ICallback<MarketResult>() {
                    @Override
                    public void action(MarketResult result) {
                        final double last = result.data.ticker.last;
                        //计算买入、卖出的价格, 双线程发出买入卖出交易指令
                        controller.trade(new CoinBigTradeItem(TradeType.Buy, last + params.priceDiff, params));
                        TimeUtil.execute(new TimerTask() {
                            @Override
                            public void run() {
                                controller.trade(new CoinBigTradeItem(TradeType.Sell, last - params.priceDiff, params));
                            }
                        }, params.getPauseTime());
                    }
                });
    }
}