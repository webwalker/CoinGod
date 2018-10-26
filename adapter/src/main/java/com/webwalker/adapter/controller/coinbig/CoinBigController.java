package com.webwalker.adapter.controller.coinbig;

import com.webwalker.adapter.controller.OrderController;
import com.webwalker.adapter.model.CommonResult;
import com.webwalker.adapter.controller.coinbig.model.DepthResult;
import com.webwalker.adapter.controller.coinbig.model.MarketResult;
import com.webwalker.adapter.model.BaseCancelItem;
import com.webwalker.adapter.model.BaseDepthItem;
import com.webwalker.adapter.model.BaseOrderItem;
import com.webwalker.adapter.model.BaseMarketItem;
import com.webwalker.adapter.http.APIStatus;
import com.webwalker.adapter.controller.AbsController;
import com.webwalker.adapter.model.BaseTradeItem;
import com.webwalker.adapter.http.JsonCallback;
import com.webwalker.adapter.controller.coinbig.model.CoinBigTradeItem;
import com.webwalker.adapter.controller.coinbig.model.OrderResult;
import com.webwalker.adapter.controller.coinbig.model.SymbolResult;
import com.webwalker.adapter.controller.coinbig.model.TradeResult;
import com.webwalker.adapter.model.TradeOrderItem;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.ICallback;
import com.webwalker.core.utility.MD5Encrypt;
import com.webwalker.core.utility.Utils;
import com.webwalker.http.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xujian on 2018/7/7.
 */
public class CoinBigController extends AbsController {
    public CoinBigController(TaskParams params) {
        super(params);
    }

    @Override
    public void symbol(final ICallback callback) {
        Map<String, Object> params = new HashMap<>();
        OkHttpUtils
                .get()
                .url(getUrl("/api/publics/v1/symbols"))
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
                .get()
                .url(getUrl("/api/publics/v1/depthList"))
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
                .get()
                .url(getUrl("/api/publics/v1/ticker"))
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

        final CoinBigTradeItem item = (CoinBigTradeItem) trade;
        Map<String, Object> params = new HashMap<>();
        params.put("type", item.getType());
        params.put("price", item.getPrice() + "");
        params.put("amount", item.amount + "");
        params.put("symbol", item.symbol);
        OkHttpUtils
                .post()
                .url(getUrl("/api/publics/v1/trade"))
                .params(buildParams(params))
                .build()
                .execute(new JsonCallback<TradeResult>() {
                    @Override
                    public void onSuccess(TradeResult result) {
                        if (result.data.result) {
                            log(item.getType() + " order succeed, price: " + trade.getPrice()
                                    + " orderId: " + result.data.order_id + ", cost_time: " + getTradeTime() + "s");
                        } else {
                            log(item.getType() + " order failed, price: " + trade.getPrice()
                                    + " orderId: " + result.data.order_id + ", cost_time: " + getTradeTime() + "s");
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
    public void order(final BaseOrderItem order, final ICallback callback) { //1秒调用限制
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", params.symbolValue);
        map.put("size", "50"); //最多50条
        map.put("type", order.type + "");
        OkHttpUtils
                .post()
                .url(getUrl("/api/publics/v1/orders_info"))
                .params(buildParams(map))
                .build()
                .execute(new JsonCallback<OrderResult>() {
                    @Override
                    public void onSuccess(OrderResult result) {
                        if (result.data.result) {
                            //添加到公共订单池
                            for (OrderResult.OrderItem.OrdersBean order : result.data.orders) {
                                TradeOrderItem item = new TradeOrderItem(params);
                                item.orderId = order.order_id + "";
                                item.count = order.count;
                                item.leftCount = order.leftCount;
                                item.price = order.price;
                                item.status = order.status;
                                item.createDate = order.create_date;
                                OrderController.add(params.accessKey, item);
                            }
                            callback.action(result);
                        } else {
                            log("get orders: " + result);
                            callback.action(null);
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
        params.put("order_id", cancel.orderId);
        OkHttpUtils
                .post()
                .url(getUrl("/api/publics/v1/cancel_order"))
                .params(buildParams(params))
                .build()
                .execute(new JsonCallback<CommonResult>() {
                    @Override
                    public void onSuccess(CommonResult result) {
                        log("cancel order succeed: " + result);
                    }

                    @Override
                    public void onFailed(APIStatus<CommonResult> status) {
                        log("cancel order failed: " + status.msg);
                    }
                });
    }

    @Override
    protected String getUrl(String url) {
        return "https://www.coinbig.com" + url;
    }

    @Override
    protected Map<String, String> buildHeaders(Map<String, String> headers) {
        return null;
    }

    @Override
    protected Map<String, Object> buildParams(Map<String, Object> map) {
        map.put("apikey", params.accessKey);
        map.put("time", System.currentTimeMillis() + "");
        map.put("sign", sign(map));
        return map;
    }

    @Override
    protected String sign(Map<String, Object> mapParams) {
        Map<String, Object> map = Utils.sort(mapParams);
        String query = Utils.mapToQuery(map);
        query += "&secret_key=" + params.secretKey;
        return MD5Encrypt.encode(query);
    }
}
