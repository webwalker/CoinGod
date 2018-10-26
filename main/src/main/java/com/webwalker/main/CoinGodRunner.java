package com.webwalker.main;

import com.webwalker.adapter.strategy.AbsStrategy;
import com.webwalker.adapter.strategy.StrategyFactory;
import com.webwalker.core.auth.AuthResolver;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.ICallback2;
import com.webwalker.core.utility.Logger;
import com.webwalker.core.utility.TimeUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CoinGodRunner {
    private static Map<String, Timer> timers = new HashMap<>();

    public static void main(String[] args) {
        //String platform = "CoinBig";
        //String key = "D4D01DA4391425DF53E1930975D63D84";
        String platform = args[0];
        String key = args[1]; //accessKey
        Logger.d(key, "begin to start >>>= " + platform + " Robot =<<<.");

        ConfigResolver.init(platform, key);
        TaskParams taskParams = ConfigResolver.config.getConfig(platform, key);
        if (!taskParams.check()) {
            return;
        }

        AbsStrategy strategy = StrategyFactory.getInstance().getStrategy(platform, taskParams);
        Timer timer = timers.get(key);
        if (timer != null) {
            timer.cancel();
        }
        final Timer finalTimer = timer;
        timer = TimeUtil.executeAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (AuthResolver.isValidCode(key, taskParams.authCode)) {
                        Logger.d(key, "Robot is running for next task.");
                        strategy.start();
                        strategy.order();
                        strategy.limit(new ICallback() {
                            @Override
                            public void action(Object result) {
                                Boolean need = (Boolean) result;
                                if (need) {
                                    finalTimer.cancel();
                                    Logger.d(key, "current task reach the limits conditions, exit.");
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    Logger.d(key, "Robot comes error, wait for next task: " + e.getMessage());
                }
            }
        }, taskParams.getRobotTime());
        timers.put(key, timer);
    }

    //taskId通常为一个账号对应的唯一标识,目前用accessKey
    public static void start(String platform, String taskId) {
        main(new String[]{platform, taskId});
    }

    public static void pause(String taskId) {
        Timer timer = timers.get(taskId);
        if (timer != null) {
            timer.cancel();
            Logger.d(taskId, "task has been canceled.");
        }
    }
}
