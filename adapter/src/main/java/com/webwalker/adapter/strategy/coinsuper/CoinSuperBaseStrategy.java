package com.webwalker.adapter.strategy.coinsuper;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.adapter.controller.OrderController;
import com.webwalker.adapter.controller.coinbig.CoinBigController;
import com.webwalker.adapter.controller.coinsuper.CoinSuperController;
import com.webwalker.adapter.strategy.CoinBaseStrategy;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.LimitOrderItem;
import com.webwalker.core.config.PlatformType;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.Logger;

import java.util.List;

/**
 * Created by xujian on 2018/7/16.
 */
public abstract class CoinSuperBaseStrategy extends CoinBaseStrategy {
    protected CoinSuperController controller;

    public CoinSuperBaseStrategy(TaskParams params) {
        super(params);
        controller = new CoinSuperController(params);
    }

    @Override
    protected AbsController getController() {
        return controller;
    }

    @Override
    public void limit(ICallback callback) {
        List<String> orders = OrderController.getTodayOrders(params.accessKey);
        if (orders == null || orders.size() == 0) {
            log("current save orders is null,  online order trade is go on.");
            return;
        }

        // TO-DO 每次50笔订单查询交易情况，计算交易金额是否已达到500
//        for (int i = 0; i < orders / 50; i++) {
//
//        }
//
//        String queryOrder = OrderController.buildOrders(orders);
//
//        //数量控制
//        controller.limitOrder(orders, new ICallback() {
//            @Override
//            public void action(Object result) {
//
//            }
//        });

        //callback.action(true);
        //callback.action(false);
    }
}
