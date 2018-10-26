package com.webwalker.adapter.controller;

import com.webwalker.adapter.model.TradeOrderItem;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.LimitOrderItem;
import com.webwalker.core.config.PlatformType;
import com.webwalker.core.utility.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xujian on 2018/7/8.
 */
public class OrderController {
    public static final int STATUS_NOT = 1; //未成交
    public static final int STATUS_PARTLY = 2; //部分成交

    public static Map<String, List<TradeOrderItem>> allOrders = new HashMap<>();
    //没有任何成交的订单
    public static Map<String, List<TradeOrderItem>> unSuccessOrders = new HashMap<>();
    //部分成交的订单
    public static Map<String, List<TradeOrderItem>> partlyOrders = new HashMap<>();

    public static void add(String key, TradeOrderItem item) {
        switch (item.status) {
            case STATUS_NOT:
                addItem(unSuccessOrders, key, item);
                break;
            case STATUS_PARTLY:
                addItem(partlyOrders, key, item);
                break;
        }
        addItem(allOrders, key, item);
    }

    private static void addItem(Map<String, List<TradeOrderItem>> orders, String key, TradeOrderItem item) {
        List<TradeOrderItem> items = orders.get(key);
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        orders.put(key, items);
    }

    public static String buildOrders(List<String> orders) {
        if (orders == null || orders.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (String s : orders) {
            sb.append(s).append(",");
        }
        if (sb.toString().length() > 0) {
            return sb.toString().substring(0, sb.toString().length() - 1);
        }
        return "";
    }

    public static List<String> getTodayOrders(String key) {
        LimitOrderItem orderItem = ConfigResolver.getLimit(PlatformType.CoinSuper.getName(), key);
        if (orderItem == null) return null;
        Map<String, List<String>> items = orderItem.orders.get(key);
        if (items == null) return null;

        String today = TimeUtil.getToday();
        List<String> item = items.get(today);
        return item;
    }

    public static int getSize(Map<String, List<TradeOrderItem>> orders, String key) {
        List<TradeOrderItem> orderItems = orders.get(key);
        if (orderItems == null) return 0;
        return orderItems.size();
    }

    public static void reset(String key) {
        unSuccessOrders.put(key, new ArrayList<>());
        partlyOrders.put(key, new ArrayList<>());
        allOrders.put(key, new ArrayList<>());
    }
}
