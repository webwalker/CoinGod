package com.webwalker.adapter.controller.coinsuper.model;

import java.util.List;

/**
 * Created by xujian on 2018/7/16.
 */
public class SymbolResult extends CoinSuperResult<SymbolResult.SymbolDataItem> {
    public static class SymbolDataItem {
        public long timestamp;
        public List<ResultBean> result;

        public static class ResultBean {
            public String symbol;
            public String quantityMin;
            public String quantityMax;
            public String priceMin;
            public String priceMax;
            public String deviationRatio;
        }
    }
}
