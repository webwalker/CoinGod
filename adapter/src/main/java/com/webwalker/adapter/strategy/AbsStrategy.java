package com.webwalker.adapter.strategy;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.Logger;

/**
 * Created by xujian on 2018/7/8.
 */
public abstract class AbsStrategy {
    protected TaskParams params;
    protected String key;

    public AbsStrategy(TaskParams params) {
        this.params = params;
        this.key = params.accessKey;
    }

    protected abstract AbsController getController();

    //是否达到限制条件或阀值
    public abstract void limit(ICallback c);

    //启动策略
    public abstract void start();

    //检查订单状态
    public abstract void order();

    protected void log(String log){
        Logger.d(key, log);
    }
}
