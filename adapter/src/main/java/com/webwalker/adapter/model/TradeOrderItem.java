package com.webwalker.adapter.model;

import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.Logger;
import com.webwalker.core.utility.TimeUtil;

import static com.webwalker.core.config.Consts.ORDER_TAG;

/**
 * Created by xujian on 2018/7/8.
 */
public class TradeOrderItem {
    public TaskParams params;
    public String orderId;
    public double price; //委托价格
    public double count; //委托数量
    public double leftCount; //剩余未成交数量
    public int status; //1未完成, 2部分成交
    public String createDate;
    public long createTime; //long型日期

    public TradeOrderItem(TaskParams params) {
        this.params = params;
    }

    //订单是否已失效, 失效则发出取消指令
    public boolean hasExpire() {
        long currentTime = System.currentTimeMillis();
        long expireTime = params.getExpireTime();
        long createTime = TimeUtil.convertTimeToLong(createDate);
        if (createTime + expireTime < currentTime) {
            Logger.d(params.accessKey, ORDER_TAG + orderId + " has expired, need to cancel.");
            return true;
        }
        Logger.d(params.accessKey, ORDER_TAG + orderId + " not expired.");
        return false;
    }

    public boolean hasExpireLong() {
        long currentTime = TimeUtil.getUTCTime();
        long expireTime = params.getExpireTime();
        if (createTime + expireTime < currentTime) {
            Logger.d(params.accessKey, ORDER_TAG + orderId + " has expired, need to cancel.");
            return true;
        }
        Logger.d(params.accessKey, ORDER_TAG + orderId + " not expired.");
        return false;
    }
}
