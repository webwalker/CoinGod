package com.webwalker.adapter.controller.coinsuper.model;

import com.webwalker.adapter.model.CommonResult;

import java.util.List;

public class DepthResult extends CoinSuperResult<DepthResult.DepthResultItem> {
    public static class DepthResultItem {
        public long timestamp;
        public ResultBean result;

        public static class ResultBean {
            public List<AsksBean> asks;
            public List<BidsBean> bids;

            public static class AsksBean {
                public String limitPrice;
                public String quantity;
            }

            public static class BidsBean {
                public String limitPrice;
                public String quantity;
            }
        }
    }
}

