package com.webwalker.core.auth;

import com.webwalker.core.utility.JsonUtil;

/**
 * Created by xujian on 2018/7/5.
 */
public class UserItem {
    public String authCode;
    public boolean isOnline;
    public long activeTime; //激活时间
    public long createTime; //创建时间
    public String ip;
    public String activeDate;

    public UserItem() {
    }

    public UserItem(String authCode) {
        this.authCode = authCode;
    }

    public UserItem(String authCode, boolean isOnline) {
        this.authCode = authCode;
        this.isOnline = isOnline;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
