package com.webwalker.adapter.controller.coinbig.model;

import com.webwalker.adapter.model.CommonResult;

import java.util.List;

public class DepthResult extends CommonResult<DepthResult.DepthResultItem> {
    public static class DepthResultItem {
        public int tradeMappingId;
        public double maxsell;
        public double maxbuy;
        public List<AsksBean> asks;
        public List<AsksBean> bids;

        public static class AsksBean {
            public String price;
            public String quantity;
            public String quantityCount;
            public double priceDec;
            public double quantityDec;
            public double quantityCountDec;
        }
    }
}

