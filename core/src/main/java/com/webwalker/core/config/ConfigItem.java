package com.webwalker.core.config;

        import java.util.List;

/**
 * Created by xujian on 2018/7/5.
 */
public class ConfigItem {
    public List<TaskParams> configs;

    //获取某一个平台的一个任务参数信息
    public TaskParams getConfig(String platform, String key) {
        for (TaskParams p : configs) {
            if (p.accessKey.toLowerCase().equals(key.toLowerCase())) {
                return p;
            }
        }
        return null;
    }
}
