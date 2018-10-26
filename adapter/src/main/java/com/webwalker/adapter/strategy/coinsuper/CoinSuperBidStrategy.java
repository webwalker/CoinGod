package com.webwalker.adapter.strategy.coinsuper;

import com.webwalker.adapter.controller.coinsuper.model.MarketResult;
import com.webwalker.adapter.model.BaseMarketItem;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;

/**
 * Created by xujian on 2018/7/8.
 */
public class CoinSuperBidStrategy extends CoinSuperBaseStrategy {
    public CoinSuperBidStrategy(TaskParams params) {
        super(params);
    }

    @Override
    public void start() {
        controller.market(new BaseMarketItem(params.accessKey, params.symbolValue),
                new ICallback<MarketResult>() {
                    @Override
                    public void action(MarketResult result) {
//                        final double buy = result.data.ticker.buy;
//                        final double sell = result.data.ticker.getSell();
//                        if (sell - buy > params.priceDiff) {
//                            log("bid strategy conditions is not ok.");
//                            return;
//                        }
//                        //计算买入、卖出的价格, 双线程发出买入卖出交易指令
//                        controller.trade(new CoinBigTradeItem(TradeType.Buy, sell, params));
//                        TimeUtil.execute(new TimerTask() {
//                            @Override
//                            public void run() {
//                                controller.trade(new CoinBigTradeItem(TradeType.Sell, buy, params));
//                            }
//                        }, params.getPauseTime());
                    }
                });
    }
}
