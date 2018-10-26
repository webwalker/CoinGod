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
import com.webwalker.core.utility.Utils;

import java.util.TimerTask;

/**
 * Created by xujian on 2018/7/8.
 */
public class CoinMidStrategy extends CoinBaseStrategy {
    private CoinBigController controller;

    public CoinMidStrategy(TaskParams params) {
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
                        double buy = result.data.ticker.buy;
                        double sell = result.data.ticker.getSell();
                        //计算买入、卖出的价格, 双线程发出买入卖出交易指令
                        final double price = Utils.getDouble((buy + sell) / 2);
                        controller.trade(new CoinBigTradeItem(TradeType.Buy, price + params.priceDiff, params));
                        TimeUtil.execute(new TimerTask() {
                            @Override
                            public void run() {
                                controller.trade(new CoinBigTradeItem(TradeType.Sell, price - params.priceDiff, params));
                            }
                        }, params.getPauseTime());
                    }
                });
    }
}