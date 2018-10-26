package com.webwalker.core.utility;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by xujian on 2018/7/5.
 */
public class Utils {
    public static String appendQueryParameter(String url, Map<String, Object> params) {
        return appendQueryParameter(url, params, false);
    }

    public static String appendQueryParameter(String url, Map<String, Object> params, boolean encode) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        if (url.contains("?")) {
            url += "&";
        } else {
            url += "?";
        }
        url = url + mapToQuery(params, encode);
        return url;
    }

    public static String mapToQuery(Map<String, Object> params) {
        return mapToQuery(params, false);
    }

    public static String mapToQuery(Map<String, Object> params, boolean encode) {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = (params.get(key) == null ? "" : params.get(key).toString());
            if (encode) {
                try {
                    value = URLEncoder.encode(value, Charset.defaultCharset().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            sb.append(key).append("=").append(value);
            if (iterator.hasNext()) {
                sb.append("&");
            }
        }
        //Logger.d(sb.toString());
        return sb.toString();
    }

    public static Map<String, Object> sort(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2); //升序
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }

    //获取4位数, 四舍五入
    public static double getDouble(double value) {
        BigDecimal bg = new BigDecimal(value);
        return bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //获取4位数, 不进行四舍五入
    public static double get4DotDouble(double value) {
        return ((int) (value * 10000) ) / 10000.0000;
    }
}
