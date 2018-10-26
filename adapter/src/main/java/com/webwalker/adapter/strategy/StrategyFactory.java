package com.webwalker.adapter.strategy;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.adapter.controller.coinbig.CoinBigController;
import com.webwalker.adapter.controller.coinsuper.CoinSuperController;
import com.webwalker.adapter.strategy.coinbig.CoinAvgStrategy;
import com.webwalker.adapter.strategy.coinbig.CoinBidStrategy;
import com.webwalker.adapter.strategy.coinbig.CoinLastStrategy;
import com.webwalker.adapter.strategy.coinbig.CoinMidStrategy;
import com.webwalker.adapter.strategy.coinsuper.CoinSuperAvgStrategy;
import com.webwalker.adapter.strategy.coinsuper.CoinSuperBidStrategy;
import com.webwalker.adapter.strategy.coinsuper.CoinSuperLastStrategy;
import com.webwalker.adapter.strategy.coinsuper.CoinSuperMidStrategy;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.TaskParams;

/**
 * Created by xujian on 2018/7/8.
 */
public class StrategyFactory {
    private static StrategyFactory instance;

    public static StrategyFactory getInstance() {
        if (instance == null) {
            instance = new StrategyFactory();
        }
        return instance;
    }

    public AbsStrategy getStrategy(String platform, TaskParams params) {
        switch (ConfigResolver.platformType(platform)) {
            case CoinBig:
                switch (ConfigResolver.strategyType(params.strategyType)) {
                    case Avg:
                        return new CoinAvgStrategy(params);
                    case Last:
                        return new CoinLastStrategy(params);
                    case Bid:
                        return new CoinBidStrategy(params);
                    case Mid:
                        return new CoinMidStrategy(params);
                }
                break;
            case CoinSuper:
                switch (ConfigResolver.strategyType(params.strategyType)) {
                    case Avg:
                        return new CoinSuperAvgStrategy(params);
                    case Last:
                        return new CoinSuperLastStrategy(params);
                    case Bid:
                        return new CoinSuperBidStrategy(params);
                    case Mid:
                        return new CoinSuperMidStrategy(params);
                }
                break;
        }
        return new NullStrategy(params);
    }

    public AbsController getController(String platform, TaskParams params) {
        switch (ConfigResolver.platformType(platform)) {
            case CoinBig:
                return new CoinBigController(params);
            case CoinSuper:
                return new CoinSuperController(params);
        }
        return null;
    }
}
