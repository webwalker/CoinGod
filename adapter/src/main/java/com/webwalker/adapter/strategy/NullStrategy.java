package com.webwalker.adapter.strategy;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;

/**
 * Created by xujian on 2018/7/8.
 */
public class NullStrategy extends AbsStrategy {
    public NullStrategy(TaskParams params) {
        super(params);
    }

    @Override
    protected AbsController getController() {
        return null;
    }

    @Override
    public void limit(ICallback c) {
    }

    @Override
    public void start() {
    }

    @Override
    public void order() {
    }
}
