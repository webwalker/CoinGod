package com.webwalker.core.utility;

import java.util.Timer;

/**
 * Created by xujian on 2018/7/8.
 */
public interface ICallback2<T> {
    void action(Timer timer, T result);
}
