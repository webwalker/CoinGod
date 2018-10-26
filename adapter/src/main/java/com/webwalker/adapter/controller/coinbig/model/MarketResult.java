package com.webwalker.adapter.controller.coinbig.model;

import com.webwalker.adapter.model.CommonResult;
import com.webwalker.core.utility.StringUtil;

/**
 * Created by xujian on 2018/7/8.
 */
public class MarketResult extends CommonResult<MarketResult.MarketItem> {
    public static class MarketItem {
        public long date;
        public MarketBean ticker;

        public static class MarketBean {
            public double high;
            public double vol;
            public double last;
            public double low;
            public double buy;
            public String sell;

            public double getSell(){
                return StringUtil.getDouble(sell);
            }
        }
    }
}
