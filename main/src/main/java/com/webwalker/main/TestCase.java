package com.webwalker.main;

import com.webwalker.adapter.controller.coinsuper.CoinSuperController;
import com.webwalker.adapter.controller.coinsuper.model.SymbolResult;
import com.webwalker.adapter.model.TradeType;
import com.webwalker.core.config.ConfigItem;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.JsonUtil;
import com.webwalker.core.utility.Logger;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xujian on 2018/7/13.
 */
public class TestCase {

    public static void main(String[] args) {
        ConfigResolver.setDebug(true);

        TaskParams params = new TaskParams();
        params.accessKey = "1";
        params.secretKey = "2";
        CoinSuperController controller = new CoinSuperController(params);
        controller.symbol(new ICallback<SymbolResult>() {
            @Override
            public void action(SymbolResult result) {

            }
        });

        //controller.market(new BaseMarketItem("BTC_USDT"));
        //controller.depth(new BaseDepthItem("BTC_USDT"));
        //controller.trade(new CoinBigTradeItem(TradeType.Buy, 6900, 0.001, "BTC_USDT"));
        //controller.cancelOrder("12323");
        //controller.detail(new BaseOrderItem("4344"));
    }

    @Test
    public void main() {
        //CoinGodRunner.start("CoinBig", "D4D01DA4391425DF53E1930975D63D84");
    }

    @Test
    public void test() {
        ConfigItem ci = new ConfigItem();
        ci.configs = new ArrayList<>();

        TaskParams tp = new TaskParams();
        tp.accessKey = "1";
        tp.amount = 0.01;

        TaskParams tp1 = new TaskParams();
        tp1.accessKey = "2";
        tp1.amount = 0.02;
        ci.configs.add(tp);
        ci.configs.add(tp1);

        TaskParams tp3 = new TaskParams();
        tp3.accessKey = "2";
        tp3.amount = 0.03;

        for (TaskParams p : ci.configs) {
            if (p.accessKey.toLowerCase().equals(tp3.accessKey.toLowerCase())) {
//                p = tp3;
                ci.configs.remove(p);
                ci.configs.add(tp3);
            }
        }

        Logger.dn(JsonUtil.toJson(ci));
    }

    public class BaseTradeItem {
        public TaskParams params;
        public TradeType type;
        public double price;
        public double amount;
        public String symbol;
    }

    @Test
    public void test1() {
        BaseTradeItem t = new BaseTradeItem();
        t.price = 1.01;
        t.amount = 1002;
        t.symbol = "fsfsf";

        Map<String, Object> maps = new HashMap<>();
        maps.put("a", "1");
        maps.put("b", t);
        Logger.dn(JsonUtil.toJson(maps));
    }

}
