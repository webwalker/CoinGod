package com.webwalker.core.config;

/**
 * Created by xujian on 2018/7/8.
 */
public enum StrategyType {
    Avg("avg"),
    Last("last"),
    Bid("bid"),
    Mid("mid");

    private final String type;

    StrategyType(String type) {
        this.type = type;
    }

    public static StrategyType getByType(String strategy) {
        for (StrategyType st : StrategyType.values()) {
            if (st.type.toLowerCase().equals(strategy.toLowerCase())) {
                return st;
            }
        }
        throw new IllegalArgumentException("StrategyType input error.");
    }

    public String getType() {
        return type;
    }
}