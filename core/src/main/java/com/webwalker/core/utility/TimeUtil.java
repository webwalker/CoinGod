package com.webwalker.core.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Smile<lijian@adeaz.com>
 * 2015-2-2
 */
public class TimeUtil {
    private static final DateFormat formatter = new SimpleDateFormat
            ("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final DateFormat formatter2 = new SimpleDateFormat
            ("yyyy-MM-dd", Locale.getDefault());
    private static String[] starArr = {"魔羯座", "水瓶座", "双鱼座", "牡羊座",
            "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座"};

    public static void execute(final TimerTask task, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                task.run();
                this.cancel();
            }
        }, delay);
    }

    public static Timer executeAtFixedRate(final TimerTask task, long period) {
        return executeAtFixedRate(task, 0, period);
    }

    public static Timer executeAtFixedRate(final TimerTask task, long delay, long period) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                task.run();
            }
        }, delay, period);
        return timer;
    }

    public static long getUTCTime() {
        TimeZone zone = TimeZone.getTimeZone("UTC");
        return Calendar.getInstance(zone).getTimeInMillis();
    }

    public static String getToday() {
        String today = TimeUtil.date2YYYYMMdd(TimeUtil.getUTCTime());
        return today;
    }

    public static String formatTime2Str(Date date) {
        return formatter.format(date);
    }

    public static String formatTime2Str(long time) {
        if (time == 0) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time + "");
            return formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getHM(Date date) {
        SimpleDateFormat fmat = new SimpleDateFormat("HH:mm");
        String time = fmat.format(date);
        return time;
    }


    public static String formatTime2Str2(Date date) {
        return formatter2.format(date);
    }

    public static Date formatStr2Time(String str) {
        Date date = new Date();
        if (StringUtil.isEmpty(str)) {
            return date;
        }
        try {
            if (str.length() > 10) {
                date = formatter.parse(str);
            } else
                date = formatter2.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 计算时间差 生成string
     */
    public static String calcuTimeAgo(Date dateNow, Date dateBefore) {
        if (dateNow == null || dateBefore == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        //初始时间1970.01.01 8:00:00,减去8小时
        calendar.setTimeInMillis(dateNow.getTime() - dateBefore.getTime() - 1000 * 60 * 60 * 8);
        if (calendar.get(Calendar.YEAR) > 1970) {
            return (calendar.get(Calendar.YEAR) - 1970) + "年前";
        }
        //月份从0开始
        if (calendar.get(Calendar.MONTH) > 0) {
            return (calendar.get(Calendar.MONTH) + 1) + "个月前";
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) > 1) {
            return (calendar.get(Calendar.DAY_OF_MONTH)) + "天前";
        }
        if (calendar.get(Calendar.HOUR_OF_DAY) > 0) {
            return (calendar.get(Calendar.HOUR_OF_DAY)) + "小时前";
        }
        if (calendar.get(Calendar.MINUTE) > 0) {
            return (calendar.get(Calendar.MINUTE)) + "分钟前";
        }
        if (calendar.get(Calendar.SECOND) > 0) {
            return (calendar.get(Calendar.SECOND)) + "秒前";
        }
        return "刚刚";
    }

    /**
     * 计算时间差 生成string
     */
    public static String calcuTimeAgoNews(Date dateNow, Date dateBefore) {
        if (dateNow == null || dateBefore == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        //初始时间1970.01.01 8:00:00,减去8小时
        calendar.setTimeInMillis(dateNow.getTime() - dateBefore.getTime() - 1000 * 60 * 60 * 8);
        if (calendar.get(Calendar.YEAR) > 1970) {
//            return (calendar.get(Calendar.YEAR) - 1970) + "年前";
            return "7天前";
        }
        //月份从0开始
        if (calendar.get(Calendar.MONTH) > 0) {
//            return (calendar.get(Calendar.MONTH) + 1) + "月前";
            return "7天前";
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) > 1) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (day > 7)
                day = 7;
            return day + "天前";
        }

        Calendar calNow = Calendar.getInstance(Locale.getDefault());
        calNow.setTime(dateNow);
        Calendar calBefore = Calendar.getInstance(Locale.getDefault());
        calBefore.setTime(dateBefore);

        if (calBefore.get(Calendar.DAY_OF_YEAR) < calNow.get(Calendar.DAY_OF_YEAR))
            return "1天前";

        if (calendar.get(Calendar.HOUR_OF_DAY) > 0) {
            return (calendar.get(Calendar.HOUR_OF_DAY)) + "小时前";
        }
        if (calendar.get(Calendar.MINUTE) > 0) {
            int minute = calendar.get(Calendar.MINUTE);
//            if (minute <= 30)
            return minute + "分钟前";
//            else
//                return "1小时前";
        }
//        if (calendar.get(Calendar.SECOND) > 0) {
//            return (calendar.get(Calendar.SECOND)) + "秒前";
//        }
        return "刚刚";
    }

    public static String calcuTimeSub(Date dateNow, Date dateBefore) {
        if (dateNow == null || dateBefore == null) {
            return "";
        }
        long diff = dateNow.getTime() - dateBefore.getTime();
        return calcuTimeSub(diff);
    }


    /**
     * 时间戳转换
     *
     * @param diff  时间戳
     * @param space 占位符（如 :  --  // 等）
     * @return
     */
    public static String calcuTimeSub(long diff, String space) {
        if (diff <= 0)
            return "";
        StringBuilder sb = new StringBuilder();
        diff = diff / 1000;
        long day = diff / (60 * 60 * 24);
        long hours = (diff - day * (60 * 60 * 24)) / (60 * 60);
        long minutes = (diff - day * (60 * 60 * 24) - hours * (60 * 60)) / (60);
        long sec = diff % 60;
        if (day > 0) {
            sb.append(day > 9 ? day : "0" + day).append(space);
        }
        if (hours > 0) {
            sb.append(hours > 9 ? hours : "0" + hours).append(space);
        }
        if (minutes > 0) {
            sb.append(minutes > 9 ? minutes : "0" + minutes).append(space);
        }
        if (sec > 0) {
            sb.append(sec > 9 ? sec : "0" + sec);
        } else {
            sb.append("00");
        }
        return sb.toString();
    }

    public static String calcuTimeSub(long diff) {
        if (diff <= 0)
            return "";
        StringBuilder sb = new StringBuilder();
        diff = diff / 1000;
        long day = diff / (60 * 60 * 24);
        long hours = (diff - day * (60 * 60 * 24)) / (60 * 60);
        long minutes = (diff - day * (60 * 60 * 24) - hours * (60 * 60)) / (60);
        long sec = diff % 60;
        if (day > 0) {
            sb.append(day).append("天");
        }
        if (hours > 0) {
            sb.append(hours).append("时");
        }
        if (minutes > 0) {
            sb.append(minutes).append("分");
        }
        if (sec > 0) {
            sb.append(sec).append("秒");
        }
        return sb.toString();
    }

    /**
     * 计算时间差 生成string
     */
    public static String calcuTimeAgo(Date dateNow, String dateBefore) {
        return calcuTimeAgo(dateNow, formatStr2Time(dateBefore));
    }

    /**
     * 计算年龄 生成int
     */
    public static int calcuAge(Date dateNow, String dateBeforeStr) {
        return calcuAge(dateNow, formatStr2Time(dateBeforeStr));
    }

    /**
     * 计算年龄 生成int
     */
    public static int calcuAge(Date dateNow, Date dateBefore) {
        if (dateNow == null || dateBefore == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int nowYear = calendar.get(Calendar.YEAR);
        calendar.setTime(dateBefore);
        return nowYear - calendar.get(Calendar.YEAR);
//		//初始时间1970.01.01 8:00:00,减去8小时
//		calendar.setTimeInMillis(dateNow.getTime() - dateBefore.getTime()-1000*60*60*8);
//		int year = calendar.get(Calendar.YEAR);
//		if (year > 1970) {
//			return year - 1970;
//		} else {
//			return 0;
//		}
    }

    public static String getAstro(int month, int day) {
        if (month < 1)
            month = 1;
        if (month > 12)
            month = 12;
        if (day < 1)
            day = 1;
        if (day > 31)
            day = 31;
        // 两个星座分割日
        int[] dayArr = {20, 19, 20, 20, 21, 21, 22, 23, 23, 23, 22, 21};
        int index = month;
        // 所查询日期在分割日之前，索引-1，否则不变
        if (day <= dayArr[month - 1]) {
            index = index - 1;
        }
        if (index >= 12)
            index = 0;
        // 返回索引指向的星座
        return starArr[index];
    }

    //获取当天（按当前传入的时区）00:00:00所对应时刻的long型值
    public static long getStartTimeOfDay(long now, String timeZone) {
        String tz = StringUtil.isEmpty(timeZone) ? "GMT+8" : timeZone;
        TimeZone curTimeZone = TimeZone.getTimeZone(tz);
        Calendar calendar = Calendar.getInstance(curTimeZone);
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 熊晓清
     * 时间戳转 ---    2017-12-14
     *
     * @param time 时间戳
     * @return
     */
    public static String date2YYYYMMdd(long time) {
        return new SimpleDateFormat("yyyyMMdd").format(new Date(time)).trim();
    }

    /**
     * @param differenceTime 时间差 单位：秒
     * @return
     */
    public static String getCountDownTime(long differenceTime) {
        int minute = (int) (differenceTime / 60);
        int second = (int) (differenceTime % 60);
        String minuteString = minute < 10 ? String.format("0%s", minute) : String.valueOf(minute);
        String secondString = second < 10 ? String.format("0%s", second) : String.valueOf(second);
        return String.format("%s:%s", minuteString, secondString);
    }

    public static Long convertTimeToLong(String time) {
        Date date = null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
