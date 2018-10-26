package com.webwalker.adapter.strategy;

import com.webwalker.adapter.controller.OrderController;
import com.webwalker.adapter.controller.coinbig.CoinBigController;
import com.webwalker.adapter.controller.coinbig.model.MarketResult;
import com.webwalker.adapter.controller.coinbig.model.OrderResult;
import com.webwalker.adapter.model.BaseCancelItem;
import com.webwalker.adapter.model.BaseMarketItem;
import com.webwalker.adapter.model.BaseOrderItem;
import com.webwalker.adapter.model.BaseTradeItem;
import com.webwalker.adapter.model.TradeOrderItem;
import com.webwalker.adapter.model.TradeType;
import com.webwalker.adapter.strategy.AbsStrategy;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.Logger;
import com.webwalker.core.utility.TimeUtil;

import java.util.List;
import java.util.TimerTask;

import static com.webwalker.adapter.controller.OrderController.partlyOrders;
import static com.webwalker.adapter.controller.OrderController.unSuccessOrders;
import static com.webwalker.core.config.Consts.ORDER_TAG;

/**
 * Created by xujian on 2018/7/9.
 */
public abstract class CoinBaseStrategy extends AbsStrategy {
    public CoinBaseStrategy(TaskParams params) {
        super(params);
    }

    @Override
    public void limit(ICallback c) {
    }

    @Override
    public void order() {
        OrderController.reset(params.accessKey);
        getController().order(new BaseOrderItem(OrderController.STATUS_NOT), new ICallback() {
            @Override
            public void action(Object result) {
                TimeUtil.execute(new TimerTask() {
                    @Override
                    public void run() {
                        getController().order(new BaseOrderItem(OrderController.STATUS_PARTLY), new ICallback<OrderResult>() {
                            @Override
                            public void action(OrderResult result) {
                                log(ORDER_TAG + "not completed num: " + OrderController.getSize(unSuccessOrders, params.accessKey)
                                        + ", partly completed num: " + OrderController.getSize(partlyOrders, params.accessKey));
                                //取消订单
                                List<TradeOrderItem> allOrders = OrderController.allOrders.get(params.accessKey);
                                if (allOrders == null) return;
                                for (final TradeOrderItem order : allOrders) {
                                    if (order.hasExpire()) {
                                        log(ORDER_TAG + order.orderId + " has expired, need to cancel.");
                                        TimeUtil.execute(new TimerTask() {
                                            @Override
                                            public void run() {
                                                getController().cancel(new BaseCancelItem(order.orderId));
                                            }
                                        }, 1100);
                                    }
                                }
                                //用市场行情价把已成交没有卖出的部分卖出去
                                if (params.partSell() && OrderController.getSize(partlyOrders, params.accessKey) > 0) {
                                    getController().market(new BaseMarketItem(params.accessKey, params.symbolValue), new ICallback<MarketResult>() {
                                        @Override
                                        public void action(MarketResult result) {
                                            List<TradeOrderItem> partlyOrders = OrderController.partlyOrders.get(params.accessKey);
                                            for (final TradeOrderItem order : partlyOrders) {
                                                double count = order.count - order.leftCount;
                                                log(ORDER_TAG + "sale partly deal order with market price "
                                                        + result.data.ticker.last + ", count:" + count);
                                                getController().trade(new BaseTradeItem(TradeType.Sell, result.data.ticker.last, count));
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }, 1000);
            }
        });
    }
}
