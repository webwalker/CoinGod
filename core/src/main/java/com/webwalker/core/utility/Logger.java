package com.webwalker.core.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xujian on 2018/7/6.
 */
public class Logger {
    private static final String TAG = "CoinGod";
    private static ILogCallback<String> callback;

    //normal
    public static void dn(String log) {
        d(TAG, log);
    }

    public static void d(String key, String log) {
        d(key, TAG, log);
    }

    public static void d(String key, String tag, String log) {
        String message = getFormatTime() + " " + tag + " -> " + log;
        sendMessage(key, message);
        System.out.println(message);
    }

    public static void en(String log) {
        e(TAG, log);
    }

    public static void e(String key, String log) {
        e(key, TAG, log);
    }

    public static void e(String key, String tag, String log) {
        String message = getFormatTime() + " " + tag + " Error -> " + log;
        sendMessage(key, message);
        System.out.println(message);
    }

    public static String getFormatTime() {
        Date ss = new Date();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format0.format(ss.getTime());
    }

    public static void sendMessage(String key, String message) {
        if (callback != null) {
            callback.action(key, message);
        }
    }

    public static void setCallback(ILogCallback<String> callback) {
        //Logger.callbacks.put(key, callback);
        Logger.callback = callback;
    }
}
