package com.webwalker.core.auth;

import com.google.gson.reflect.TypeToken;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.Consts;
import com.webwalker.core.utility.DesEncrypt;
import com.webwalker.core.utility.FileUtil;
import com.webwalker.core.utility.JsonUtil;
import com.webwalker.core.utility.Logger;
import com.webwalker.core.utility.StringUtil;
import com.webwalker.core.utility.TimeUtil;

import org.junit.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by xujian on 2018/7/4.
 */
public class AuthResolver {
    private static final String TEST_USER = "18930976690";
    public static boolean validUser;
    private String path = "/Users/xujian/GitHub/CoinGod/main";

    //每隔10秒检查一次
    public static boolean isOnline(String key, String authCode) {
        //用户只从初始化配置, 没有的用户作为非法用户
        UserItem user = get(authCode);
        if (user == null) {
            validUser = true;
            Logger.d(key, "invalid auth user, please contact with QQ/WeChat:14876534");
            return true;
        }
        //防止合法的用户一号多用、多传播
        long currentTime = System.currentTimeMillis();
        long activeTime = user.activeTime;
        if (currentTime - activeTime < 30000) { //用户还是在线状态
            Logger.d(key, "auth code has been used online, please make sure and wait for 30 seconds!");
            return true;
        }

        //Logger.d(user.authCode);
        user.authCode = authCode;
        user.isOnline = true;
        user.activeTime = System.currentTimeMillis();
        saveValue(authCode, user.toString());
        Logger.d(key, "Auth successful! please enjoy it.");
        return false;
    }

    private static UserItem get(String key) {
        try {
            Jedis jedis = new Jedis(getServer(), 6379);
            jedis.auth(getPass());//password
            //select db默认为0
            jedis.select(0);
            String value = jedis.get(key);
            UserItem userItem = JsonUtil.fromJson(value, UserItem.class);
            jedis.quit();
            jedis.close();
            return userItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void saveValue(String key, String value) {
        try {
            Jedis jedis = new Jedis(getServer(), 6379);
            jedis.auth(getPass());//password
            //select db默认为0
            jedis.select(0);
            jedis.set(key, value);
            jedis.quit();
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveUser(String key) {
        UserItem user = new UserItem();
        user.authCode = key;
        user.createTime = System.currentTimeMillis();
        saveValue(key, user.toString());
    }

    private static String getServer() {
        return DesEncrypt.decrypt(Consts.DB_SERVER);
    }

    private static String getPass() {
        return DesEncrypt.decrypt(Consts.DB_PASS);
    }

    private void batchly(List<String> users) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(200);
        config.setMaxTotal(300);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        JedisPool pool = new JedisPool(config, getServer(), 6379, 3000, getPass());
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(0);

            for (String s : users) {
                jedis.set(s, new UserItem(s).toString());
                Logger.d("", "add " + jedis.get(s));
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        pool.destroy();
    }

    private String getAuthUser(String user) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2999, 9, 10);
        String inputString = user + "#" + calendar.getTimeInMillis();
        return DesEncrypt.encrypt(inputString);
    }

    //2天试用
    private String getTryUser(String user) {
        String inputString = user + "#" + (System.currentTimeMillis() + 2 * 60 * 60 * 1000);
        return DesEncrypt.encrypt(inputString);
    }

    public static long getExpireTime(String authCode) {
        String userCode = DesEncrypt.decrypt(authCode);
        if (!StringUtil.isEmpty(userCode) && userCode.split("#").length > 1) {
            String time = userCode.split("#")[1];
            return Long.parseLong(time);
        }
        return 0;
    }

    public static boolean isValidCode(String key, String authCode) {
        //检查是否存在
        UserItem user = get(authCode);
        if (user == null) {
            Logger.d(key, "invalid auth code, please contact with QQ/WeChat:14876534");
            return false;
        }
        //首次设定激活时间
        if (user.activeTime == 0) {
            user.activeTime = System.currentTimeMillis();
            saveValue(authCode, user.toString());
        }
        //检查有效性
        long expireTime = getExpireTime(authCode);
        long leftTime = expireTime - System.currentTimeMillis();
        if (leftTime < 0) {
            Logger.d(key, "auth code has expired, please contact with QQ/WeChat:14876534");
            return false;
        }
        Logger.d(key, "Auth successful! please enjoy it.");
        return true;
    }

    @Test
    public void createUser() { //正式用户
        List<String> users = new ArrayList<>();
        users.add(getAuthUser(TEST_USER));
        users.add(getAuthUser("10001"));
        users.add(getAuthUser("10002"));
        users.add(getAuthUser("10003"));
        users.add(getAuthUser("10004"));
        users.add(getAuthUser("10005"));
        users.add(getAuthUser("10006"));
        users.add(getAuthUser("10007"));
        users.add(getAuthUser("10008"));
        users.add(getAuthUser("10009"));
        users.add(getAuthUser("10010"));
        users.add(getAuthUser("10011"));
        users.add(getAuthUser("10012"));
        users.add(getAuthUser("10013"));
        users.add(getAuthUser("10014"));
        users.add(getAuthUser("10015"));
        users.add(getAuthUser("10016"));
        users.add(getAuthUser("10017"));
        users.add(getAuthUser("10018"));
        users.add(getAuthUser("10019"));
        users.add(getAuthUser("10020"));
        users.add(getAuthUser("10021"));
        users.add(getAuthUser("10022"));
        users.add(getAuthUser("10023"));
        users.add(getAuthUser("10024"));
        users.add(getAuthUser("10025"));
        users.add(getAuthUser("10026"));
        users.add(getAuthUser("10027"));
        users.add(getAuthUser("10028"));
        users.add(getAuthUser("10029"));
        users.add(getAuthUser("10030"));

        String authUsers = JsonUtil.toJson(users);
        String name = path + "/user_" + System.currentTimeMillis() + ".json";
        FileUtil.writeFile(authUsers, name, false);
    }

    @Test
    public void createTryUser() { //试用版
        List<String> users = new ArrayList<>();
        users.add(getTryUser("1000001"));
        users.add(getTryUser("1000002"));
        users.add(getTryUser("1000003"));
        users.add(getTryUser("1000004"));
        users.add(getTryUser("1000005"));
        users.add(getTryUser("1000006"));
        users.add(getTryUser("1000007"));
        users.add(getTryUser("1000008"));
        users.add(getTryUser("1000009"));
        users.add(getTryUser("1000010"));

        FileUtil.writeFile(JsonUtil.toJson(users),
                path + "/users_try_" + System.currentTimeMillis() + ".json",
                false);
    }

    @Test
    public void checkAuthCode() {
        if (AuthResolver.isValidCode("", "900F141B5E5FEFA1DECF19FAA815D5F1E2061D6694979FDD")) {
            return;
        }
    }

    @Test
    public void onlineSync() {
        String users = FileUtil.readFile(path + "/online.json");
        List<String> list = JsonUtil.fromJson(users, new TypeToken<List<String>>() {
        }.getType());
        for (String s : list) {
            saveUser(s);
        }
    }

    @Test
    public void queryActiveStatus() {
        String name = path + "/online.json";
        String users = FileUtil.readFile(name);
        List<String> list = JsonUtil.fromJson(users, new TypeToken<List<String>>() {
        }.getType());

        List<UserItem> onlines = new ArrayList<>();
        for (String s : list) {
            UserItem item = get(s);
            if (item != null) {
                item.activeDate = TimeUtil.formatTime2Str(item.activeTime);
                onlines.add(item);
            }
        }
        FileUtil.writeFile(JsonUtil.toJson(onlines),
                "/Users/xujian/GitHub/CoinGod/main/user_status.json", false);
    }
}
