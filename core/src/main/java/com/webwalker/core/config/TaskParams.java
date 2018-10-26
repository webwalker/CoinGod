package com.webwalker.core.config;

import com.webwalker.core.utility.Logger;
import com.webwalker.core.utility.StringUtil;

/**
 * Created by xujian on 2018/7/13.
 */
public class TaskParams {
    public String accessKey = "";
    public String secretKey = "";
    public String authCode;
    public String strategyType;
    public int depthNum;
    public double avgValue;
    public double amount;
    public double priceDiff;
    public int symbolIndex;
    public String symbolValue;
    public long robotTime;
    public long pauseTime;
    public int expireTime;
    public int partSell;
    public int limitOrder;
    public int stopEnable;
    public int stopTime;
    public int stopNum;

    public boolean partSell() {
        return partSell == 1;
    }

    public boolean limitOrder() {
        return limitOrder == 1;
    }

    public long getRobotTime() {
        return robotTime * 1000;
    }

    public void setRobotTime(long robotTime) {
        this.robotTime = robotTime;
    }

    public long getPauseTime() {
        return pauseTime * 1000;
    }

    public void setPauseTime(long pauseTime) {
        this.pauseTime = pauseTime;
    }

    public int getExpireTime() {
        return expireTime * 1000;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public boolean check() {
        if (StringUtil.isEmpty(accessKey)) {
            Logger.d(accessKey, "accessKey input error.");
            return false;
        }
        if (StringUtil.isEmpty(secretKey)) {
            Logger.d(accessKey, "secretKey input error.");
            return false;
        }
        if (StringUtil.isEmpty(authCode)) {
            Logger.d(accessKey, "authCode input error.");
            return false;
        }
        if (StringUtil.isEmpty(strategyType)) {
            Logger.d(accessKey, "strategyType input error.");
            return false;
        }
        if (StringUtil.isEmpty(symbolValue)) {
            Logger.d(accessKey, "symbolValue input error.");
            return false;
        }
        if (robotTime <= 4) {
            Logger.d(accessKey, "robotTime input error, need to be 5 seconds above.");
            return false;
        }
        return true;
    }
}
