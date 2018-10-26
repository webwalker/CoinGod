package com.webwalker.core.config;

import com.webwalker.core.utility.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xujian on 2018/7/16.
 */
public class LimitOrderItem {
    public Map<String, Map<String, List<String>>> orders;

    public void put(String key, String orderId) {
        if (orders == null) orders = new HashMap<>();
        Map<String, List<String>> items = orders.get(key);
        if (items == null) items = new HashMap<>();

        String today = TimeUtil.getToday();
        List<String> item = items.get(today);
        if (item == null) item = new ArrayList<>();
        if (!item.contains(today)) {
            item.add(orderId);
        }
        items.put(today, item);

        orders.put(key, items);
    }
}
