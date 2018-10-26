package com.webwalker.core.config;

/**
 * Created by xujian on 2018/7/8.
 */
public enum PlatformType {
    CoinBig("CoinBig"),
    CoinSuper("CoinSuper"),
    ZBG("ZBG"),
    EXX("EXX");

    private final String name;

    PlatformType(String name) {
        this.name = name;
    }

    public static PlatformType getByName(String name) {
        for (PlatformType pt : PlatformType.values()) {
            if (pt.name.toLowerCase().equals(name.toLowerCase())) {
                return pt;
            }
        }
        throw new IllegalArgumentException("PlatformType input error.");
    }

    public String getName() {
        return name;
    }
}