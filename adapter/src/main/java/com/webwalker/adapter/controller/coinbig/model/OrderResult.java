package com.webwalker.adapter.controller.coinbig.model;

import com.webwalker.adapter.model.CommonResult;

import java.util.List;

/**
 * Created by xujian on 2018/7/7.
 */
public class OrderResult extends CommonResult<OrderResult.OrderItem> {
    public static class OrderItem{
        public boolean result;
        public List<OrdersBean> orders;

        public static class OrdersBean {
            public double price;
            public double count;
            public int isLimit;//是否是限价单，0为限价单，1市价单
            public String create_date;
            public long order_id;
            public int status;
            public double leftCount;
        }
    }
}
