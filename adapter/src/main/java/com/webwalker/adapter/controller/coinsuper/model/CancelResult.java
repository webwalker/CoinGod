package com.webwalker.adapter.controller.coinsuper.model;

/**
 * Created by xujian on 2018/7/7.
 */
public class CancelResult extends CoinSuperResult<CancelResult.CancelItem> {
    public static class CancelItem {
        public long timestamp;
        public ResultBean result;

        public static class ResultBean {
            public String operate;
        }
    }
}
