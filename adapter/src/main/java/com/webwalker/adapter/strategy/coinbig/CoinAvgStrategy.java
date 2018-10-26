package com.webwalker.adapter.strategy.coinbig;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.adapter.controller.coinbig.CoinBigController;
import com.webwalker.adapter.controller.coinbig.model.CoinBigTradeItem;
import com.webwalker.adapter.controller.coinbig.model.DepthResult;
import com.webwalker.adapter.model.BaseDepthItem;
import com.webwalker.adapter.model.TradeType;
import com.webwalker.adapter.strategy.CoinBaseStrategy;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.ListUtil;
import com.webwalker.core.utility.TimeUtil;
import com.webwalker.core.utility.Utils;

import java.util.TimerTask;

/**
 * Created by xujian on 2018/7/8.
 */
public class CoinAvgStrategy extends CoinBaseStrategy {
    private CoinBigController controller;

    public CoinAvgStrategy(TaskParams params) {
        super(params);
        controller = new CoinBigController(params);
    }

    @Override
    protected AbsController getController() {
        return controller;
    }

    @Override
    public void start() {
        controller.depth(new BaseDepthItem(params.symbolValue),
                new ICallback<DepthResult>() {
                    @Override
                    public void action(DepthResult result) {
                        //获取卖单深度计算平均价
                        double askAvgPrice = ListUtil.sortAndAvg(result.data.asks, "priceDec",
                                ListUtil.SORT_ASC, params.depthNum);
                        //获取买单深度计算平均价
                        double bidAvgPrice = ListUtil.sortAndAvg(result.data.bids, "priceDec",
                                ListUtil.SORT_DESC, params.depthNum);
                        //比较均价
                        double bidPrice = Utils.getDouble(bidAvgPrice * (1 + params.avgValue));
                        if (askAvgPrice > bidPrice) {
                            log("avg strategy conditions is not ok, please wait for it."
                                    + ", AskAvgPrice:" + askAvgPrice
                                    + ", BidAvgPrice:" + bidAvgPrice
                                    + ", ConditionPrice:" + bidPrice);
                            return;
                        }
                        log("avg strategy conditions is ok, now execute the trade order!"
                                + " AskAvgPrice:" + askAvgPrice
                                + ", BidAvgPrice:" + bidAvgPrice
                                + ", ConditionPrice:" + bidPrice);
                        //计算买入、卖出的价格, 双线程发出买入卖出交易指令
                        double buyFirstPrice = result.data.asks.get(0).priceDec;
                        double sellFirstPrice = result.data.bids.get(0).priceDec;
                        final double price = Utils.getDouble((buyFirstPrice + sellFirstPrice) / 2);
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
