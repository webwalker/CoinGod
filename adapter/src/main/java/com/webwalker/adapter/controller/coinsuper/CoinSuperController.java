package com.webwalker.adapter.controller.coinsuper;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.adapter.controller.OrderController;
import com.webwalker.adapter.controller.coinsuper.model.CancelResult;
import com.webwalker.adapter.controller.coinsuper.model.CoinSuperTradeItem;
import com.webwalker.adapter.controller.coinsuper.model.CommonRequestItem;
import com.webwalker.adapter.controller.coinsuper.model.DepthResult;
import com.webwalker.adapter.controller.coinsuper.model.MarketResult;
import com.webwalker.adapter.controller.coinsuper.model.OrderDetailsResult;
import com.webwalker.adapter.controller.coinsuper.model.OrderResult;
import com.webwalker.adapter.controller.coinsuper.model.SymbolResult;
import com.webwalker.adapter.controller.coinsuper.model.TradeResult;
import com.webwalker.adapter.http.APIStatus;
import com.webwalker.adapter.http.JsonCallback;
import com.webwalker.adapter.model.BaseCancelItem;
import com.webwalker.adapter.model.BaseDepthItem;
import com.webwalker.adapter.model.BaseMarketItem;
import com.webwalker.adapter.model.BaseOrderItem;
import com.webwalker.adapter.model.BaseTradeItem;
import com.webwalker.adapter.model.TradeOrderItem;
import com.webwalker.adapter.model.TradeType;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.LimitOrderItem;
import com.webwalker.core.config.PlatformType;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.Logger;
import com.webwalker.core.utility.MD5Encrypt;
import com.webwalker.core.utility.StringUtil;
import com.webwalker.core.utility.TimeUtil;
import com.webwalker.core.utility.Utils;
import com.webwalker.http.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xujian on 2018/7/7.
 */
public class CoinSuperController extends AbsController {
    private static final Object lockLimit = new Object();

    public CoinSuperController(TaskParams params) {
        super(params);
    }

    @Override
    public void symbol(final ICallback callback) {
        Map<String, Object> params = new HashMap<>();
        OkHttpUtils
                .postJson()
                .url(getUrl("/api/v1/market/symbolList"))
                .headers(buildHeaders(null))
                .params(buildParams(params))
                .build()
                .execute(new JsonCallback<SymbolResult>() {
                    @Override
                    public void onSuccess(SymbolResult result) {
                        log("get symbol: " + result);
                        callback.action(result);
                    }

                    @Override
                    public void onFailed(APIStatus<SymbolResult> status) {
                        log("get symbol: " + status.msg);
                    }
                });
    }

    @Override
    public void depth(BaseDepthItem depth, final ICallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", depth.symbol);
        OkHttpUtils
                .postJson()
                .url(getUrl("/api/v1/market/depth"))
                .headers(buildHeaders(null))
                .params(buildParams(params))
                .build()
                .execute(new JsonCallback<DepthResult>() {
                    @Override
                    public void onSuccess(DepthResult result) {
                        log("get avg depth.");
                        callback.action(result);
                    }

                    @Override
                    public void onFailed(APIStatus<DepthResult> status) {
                        log("get avg depth: " + status.msg);
                    }
                });
    }

    @Override
    public void market(BaseMarketItem market, final ICallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", market.symbol);
        OkHttpUtils
                .postJson()
                .url(getUrl("/api/v1/market/tickers"))
                .headers(buildHeaders(null))
                .params(buildParams(params))
                .build()
                .execute(new JsonCallback<MarketResult>() {
                    @Override
                    public void onSuccess(MarketResult result) {
                        log("get market: " + result);
                        callback.action(result);
                    }

                    @Override
                    public void onFailed(APIStatus<MarketResult> status) {
                        log("get market: " + status.msg);
                    }
                });
    }

    @Override
    public void trade(final BaseTradeItem trade) { //3秒一次
        super.trade(trade);

        final CoinSuperTradeItem item = (CoinSuperTradeItem) trade;
        String url = "";
        if (item.type == TradeType.Buy) {
            url = "/api/v1/order/buy";
        } else {
            url = "/api/v1/order/sell";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", item.symbol);
        map.put("priceLimit", item.getPrice() + "");
        map.put("orderType", item.getType());
        if (item.type == TradeType.Buy_Market || item.type == TradeType.Sell_Market) {
            map.put("amount", item.amount + "");
        } else {
            map.put("quantity", item.amount + "");
        }

        OkHttpUtils
                .postJson()
                .url(getUrl(url))
                .headers(buildHeaders(null))
                .params(buildParams(map))
                .build()
                .execute(new JsonCallback<TradeResult>() {
                    @Override
                    public void onSuccess(TradeResult result) {
                        synchronized (lockLimit) { //统计限额
                            log("save order to local: " + result.data.result.orderNo);
                            LimitOrderItem orderItem = ConfigResolver.getLimit(PlatformType.CoinSuper.getName(), params.accessKey);
                            orderItem.put(params.accessKey, result.data.result.orderNo);
                            ConfigResolver.saveLimit(PlatformType.CoinSuper.getName(),orderItem);

                            log(item.getType() + " order succeed, price: " + trade.getPrice()
                                    + " orderId: " + result.data.result.orderNo + ", cost_time: " + getTradeTime() + "s");
                        }
                    }

                    @Override
                    public void onFailed(APIStatus<TradeResult> status) {
                        log(item.getType() + " order failed: " + status.msg
                                + ", price: " + item.getPrice() + " cost_time: " + getTradeTime() + "s");
                    }
                });
    }

    @Override
    public void order(final BaseOrderItem order, final ICallback callback) {
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", params.symbolValue);
        map.put("num", "50");
        OkHttpUtils
                .postJson()
                .url(getUrl("/api/v1/order/openList"))
                .headers(buildHeaders(null))
                .params(buildParams(map))
                .build()
                .execute(new JsonCallback<OrderResult>() {
                    @Override
                    public void onSuccess(OrderResult result) {
                        String orders = OrderController.buildOrders(result.data.result);
                        if (!StringUtil.isEmpty(orders)) {
                            log("not completed orders: " + orders);
                            orderLists(orders, callback);
                        }
                    }

                    @Override
                    public void onFailed(APIStatus<OrderResult> status) {
                        log("get orders: " + status.msg);
                        callback.action(null);
                    }
                });
    }

    @Override
    public void cancel(BaseCancelItem cancel) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", cancel.orderId);
        OkHttpUtils
                .postJson()
                .url(getUrl("/api/v1/order/cancel"))
                .headers(buildHeaders(null))
                .params(buildParams(params))
                .build()
                .execute(new JsonCallback<CancelResult>() {
                    @Override
                    public void onSuccess(CancelResult result) {
                        log("cancel order succeed: " + result);
                    }

                    @Override
                    public void onFailed(APIStatus<CancelResult> status) {
                        log("cancel order failed: " + status.msg);
                    }
                });
    }

    @Override
    protected String getUrl(String url) {
        return "https://api.coinsuper.com" + url;
    }

    @Override
    protected Map<String, String> buildHeaders(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("Content-Type", "application/json");
        return map;
    }

    @Override
    protected Map<String, Object> buildParams(Map<String, Object> map) {
        //构造签名信息
        long time = TimeUtil.getUTCTime();
        Map<String, Object> signMap = new HashMap<>();
        signMap.put("timestamp", time);
        signMap.putAll(map);
        String sign = sign(signMap);
        //构造请求对象
        Map<String, Object> newParams = new HashMap<>();
        //common
        CommonRequestItem req = new CommonRequestItem();
        req.accesskey = params.accessKey;
        req.sign = sign;
        req.timestamp = time;
        newParams.put("common", req);
        newParams.put("data", map);
        return newParams;
    }

    @Override
    protected String sign(Map<String, Object> map) {
        map.put("accesskey", params.accessKey);
        map.put("secretkey", params.secretKey);
        Map<String, Object> signParams = Utils.sort(map);
        String query = Utils.mapToQuery(signParams);
        if (ConfigResolver.debug) {
            Logger.dn("signStr:" + query);
        }
        return MD5Encrypt.encode(query);
    }

    //获取用户委托单状态(单次查询最大限50条记录)
    public void orderLists(String orders, final ICallback callback) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNoList", orders);
        OkHttpUtils
                .postJson()
                .url(getUrl("/api/v1/order/list"))
                .headers(buildHeaders(null))
                .params(buildParams(map))
                .build()
                .execute(new JsonCallback<OrderDetailsResult>() {
                    @Override
                    public void onSuccess(OrderDetailsResult result) {
                        //添加到公共订单池
                        for (OrderDetailsResult.OrderDetailItem.ResultBean order : result.data.result) {
                            TradeOrderItem item = new TradeOrderItem(params);
                            item.orderId = order.orderNo + "";
                            item.count = order.quantity;
                            item.leftCount = order.quantityRemaining;
                            item.price = order.priceLimit;
                            if (order.state.equals("UNDEAL")) {
                                item.status = 1;
                            } else if (order.state.equals("PARTDEAL")) {
                                item.status = 2;
                            }
                            item.createTime = order.utcCreate;
                            OrderController.add(params.accessKey, item);
                        }
                        callback.action(result);
                    }

                    @Override
                    public void onFailed(APIStatus<OrderDetailsResult> status) {
                        log("get orders: " + status.msg);
                        callback.action(null);
                    }
                });
    }

    public void limitOrder(String orders, final ICallback callback) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNoList", orders);
        OkHttpUtils
                .postJson()
                .url(getUrl("/api/v1/order/details"))
                .headers(buildHeaders(null))
                .params(buildParams(map))
                .build()
                .execute(new JsonCallback<OrderDetailsResult>() {
                    @Override
                    public void onSuccess(OrderDetailsResult result) {
                        //金额计算是否超限
                        for (OrderDetailsResult.OrderDetailItem.ResultBean order : result.data.result) {
                            TradeOrderItem item = new TradeOrderItem(params);
                            item.orderId = order.orderNo + "";
                            item.count = order.quantity;
                            item.leftCount = order.quantityRemaining;
                            item.price = order.priceLimit;
                            if (order.state.equals("UNDEAL")) {
                                item.status = 1;
                            } else if (order.state.equals("PARTDEAL")) {
                                item.status = 2;
                            }
                            item.createTime = order.utcCreate;
                            OrderController.add(params.accessKey, item);
                        }
                        callback.action(result);
                    }

                    @Override
                    public void onFailed(APIStatus<OrderDetailsResult> status) {
                        log("get orders: " + status.msg);
                        callback.action(null);
                    }
                });
    }
}
