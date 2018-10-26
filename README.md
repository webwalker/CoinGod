## CoinGod 使用手册

自动卖出

后期多任务支持

后期考虑混淆、加固，目前混淆有些问题，加固费用太高

jre整体打包或者sh、bat

## 使用手册

尽量小额交易，可以快速完成订单和规避风险

1、CoinBig平台先注册并且认证通过，注册地址：https://www.coinbig.com/userreg.html?r=722037

2、在个人中心--API密钥中创建一对私钥，记下accessid和secretkey，分别将两者填入config.properties文件的ACCESS_KEY和SECRET_KEY中

3、权衡收益和成交概率，自定义其他参数，后文附参数说明

4、然后运行CoinGod挖矿机器人。

bat、sh、apk 加紧速度开发测试

其他参数说明：

AUTH_CODE:授权码，在试用版中不起作用，正式版购买时会提供唯一的授权码方可运行程序

AMOUNT:单次交易的币数量，根据自己的资金能力来设定

SYMBOL:交易对，例如btcusdt,btmbch等

EXPIRE_TIME:已下单未成交的订单的失效时间，超过该时间，程序会自动取消订单，单位:分钟

STRATEGY_TYPE:下单策略，目前支持两种：

    1）last:最新成交价，低卖高买和低买高卖均可实现(通过LAST_DIFF参数），不一定能快速成交

    2）bid-ask: 盘口价差，只能实现高买低卖，但能快速成交

PRICE_DIFF:当下单策略为盘口价差(bid-ask)时，判断盘口的价差，当价差在一定范围内，以btcusdt为例，买一价1234.01 卖一价1234.02，如果该值设定为0.01，即为满足的条件，此时执行双向下单，买入价和卖出价分别为1234.02和1234.01，即可马上成交，该值设定越大，条件越能满足，成交概率越大，但是会亏损一定的价差，反之亦然。对于btcusdt而言，建议该值设为0.01

DIFF_PERCENT:指定达到难度的多少百分比时停止挖矿，为了避免平台api的获取延时造成的超刷

LAST_DIFF：当下单策略为最新成交价(last)时，买入价和卖出价跟当前价的价差，以btcusdt来说，最新成交价为1234.05，如果此值设定为0.01，则买入价和卖出价分别为1234.06和1234.04，即买入价大于卖出价，但是，可以设置-0.01，此时就变成买入价和卖出价分别为1234.04和1234.06

AUTO_SELL:是否需要在账户收到返还的CET时自动卖出，yes表示自动卖出，no表示不卖出

START_MN:指定每个小时段的启动时间分钟值，举个例子，我想在每个小时段的40分钟开始挖矿，一直挖到下一个小时段的开始或者满足设定的难度值的百分比

RETAIN_NUM:设定需要保留的cet数量，超过此值即刻以市价卖出

IS_BALANCE: 当其中一个交易对数量不足时，是否平仓,自动卖出富余的一方

## 参考平台

1、CET: https://www.coinex.com/

2、abcc: http://abcc.com

3、ucoin: http://www.ucoin.pw

4、CEO：https://ceo.bi/

5、CoinBig

https://www.coinbig.com/userreg.html?r=722037

https://github.com/coinbigapi/coinbigapi/wiki/API-Document


6、zbg

https://www.zbg.com/r/N2VTMlZZbThDeTg=

7、exx

https://www.exx.com/


我的：
D4D01DA4391425DF53E1930975D63D84
314B52083989635D7CD6B554F9107F38

老婆的
apikey:
C1E78DB12C9B1B3BE3B10C1DA6850E0A
secretKey:
532CD3E0309D93C91BC508B88197E8EE

压缩密码：
walker800

ZB:
7eS3E6x2XuC7eS3E6x2XuD
8688e5e7991fdca6875db1de75eff10a