package com.webwalker.core.utility;

/**
 * Created by xujian on 2018/7/8.
 */
public interface ILogCallback<T> {
    void action(String key, T result);
}
