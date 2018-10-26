package com.webwalker.adapter.strategy.coinsuper;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.adapter.controller.coinbig.CoinBigController;
import com.webwalker.adapter.controller.coinbig.model.CoinBigTradeItem;
import com.webwalker.adapter.controller.coinsuper.model.DepthResult;
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
public class CoinSuperAvgStrategy extends CoinSuperBaseStrategy {
    public CoinSuperAvgStrategy(TaskParams params) {
        super(params);
    }

    @Override
    public void start() {
        controller.depth(new BaseDepthItem(params.symbolValue),
                new ICallback<DepthResult>() {
                    @Override
                    public void action(DepthResult result) {
                    }
                });
    }
}
