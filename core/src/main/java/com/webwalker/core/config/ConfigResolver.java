package com.webwalker.core.config;

import com.webwalker.core.utility.FileUtil;
import com.webwalker.core.utility.JsonUtil;
import com.webwalker.core.utility.Logger;
import com.webwalker.core.utility.StringUtil;
import com.webwalker.core.utility.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xujian on 2018/7/5.
 */
public class ConfigResolver {
    public static boolean debug = false;
    public static String configPath = "";
    public static String limitPath = "";
    public static ConfigItem config = new ConfigItem();
    private final static String configFile = "/config.json";

    public static void setDebug(boolean debug) {
        ConfigResolver.debug = debug;
    }

    public static void init(String platform, String key) {
        config = getConfig(platform);
        if (config == null) {
            Logger.d(key, "config input parameters error.");
            return;
        }
        TaskParams task = config.getConfig(platform, key);
        if (task == null) {
            Logger.d(key, "task input parameters error.");
            return;
        }
        //Logger.d(key, "load config: " + json.toString());
        Logger.d(key, "accessKey: " + task.accessKey + ", strategy: " + task.strategyType);
    }

    public static String getProjectPath() {
        java.net.URL url = ConfigResolver.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        java.io.File file = new java.io.File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }

    public static PlatformType platformType(String platform) {
        return PlatformType.getByName(platform);
    }

    public static StrategyType strategyType(String strategyType) {
        return StrategyType.getByType(strategyType);
    }

    public static ConfigItem getConfig(String platform) {
        if (ConfigResolver.debug) {
            String json = FileUtil.readFile(getTestPath(platform));
            ConfigItem c = JsonUtil.fromJson(json, ConfigItem.class);
            return c;
        } else {
            String path = getConfigPath(platform);
            String json = FileUtil.readFile(path);
            ConfigItem c = JsonUtil.fromJson(json, ConfigItem.class);
            return c;
        }
    }

    public static void saveConfig(String platform, ConfigItem c) {
        if (ConfigResolver.debug) {
            String path = getTestPath(platform);
            ConfigResolver.configPath = path;
            FileUtil.writeFile(JsonUtil.toJson(c), path, false);
        } else {
            String path = getConfigPath(platform);
            ConfigResolver.configPath = path;
            FileUtil.writeFile(JsonUtil.toJson(c), path, false);
        }
    }

    //获取本地记录的已成功交易的订单号
    public static LimitOrderItem getLimit(String platform, String key) {
        LimitOrderItem c;
        if (ConfigResolver.debug) {
            String json = FileUtil.readFile(getLimitTestPath(platform));
            c = JsonUtil.fromJson(json, LimitOrderItem.class);
        } else {
            String path = getLimitPath(platform);
            String json = FileUtil.readFile(path);
            c = JsonUtil.fromJson(json, LimitOrderItem.class);
        }
        if (c == null) c = new LimitOrderItem();

        //移除不是当天的订单信息
        if (c.orders != null){
            Map<String, List<String>> orderItem =  c.orders.get(key);
            if (orderItem != null){
                Map<String, List<String>> result = new HashMap<>();
                String today = TimeUtil.getToday();
                for (Map.Entry<String, List<String>> entry : orderItem.entrySet()) {
                    if (entry.getKey().equals(today)){
                        result.put(today, entry.getValue());
                        break;
                    }
                }
                c.orders.put(key, result);
            }
        }

        return null;
    }

    public static void saveLimit(String platform, LimitOrderItem c) {
        if (ConfigResolver.debug) {
            String path = getLimitTestPath(platform);
            ConfigResolver.limitPath = path;
            FileUtil.writeFile(JsonUtil.toJson(c), path, false);
        } else {
            String path = getLimitPath(platform);
            ConfigResolver.limitPath = path;
            FileUtil.writeFile(JsonUtil.toJson(c), path, false);
        }
    }

    public static String getConfigPath(String platform) {
        String path = "C:\\CoinGod\\" + platform + ".json";
        return path;
    }

    private static String getTestPath(String platform) {
        return "/Users/xujian/" + platform + ".json";
    }

    public static String getLimitPath(String platform) {
        String path = "C:\\CoinGod\\" + platform + "_limit.json";
        return path;
    }

    public static String getLimitTestPath(String platform) {
        return "/Users/xujian/" + platform + "_limit.json";
    }
}
