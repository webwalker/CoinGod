package com.webwalker.core.utility;

/**
 * 字符串处理相关工具类
 */
public class StringUtil {
    public static String join(String sp, Object[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (sp != null && i != 0) {
                    stringBuilder.append(sp);
                }
                stringBuilder.append(String.valueOf(array[i]));
            }
        }

        return stringBuilder.toString();
    }

    public static int getInt(String value) {
        return getInt(value, -1);
    }

    public static int getInt(String value, int defaultValue) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static float getFloat(String value) {
        return getFloat(value, -1);
    }

    public static float getFloat(String value, float defaultValue) {
        try {
            return Float.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static double getDouble(String value) {
        return getDouble(value, -1);
    }

    public static double getDouble(String value, double defaultValue) {
        try {
            return Double.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static long getLong(String value) {
        return getLong(value, -1);
    }

    public static long getLong(String value, long defaultValue) {
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static boolean getBoolean(String value) {
        return getBoolean(value, false);
    }

    public static boolean getBoolean(String value, boolean defaultValue) {
        try {
            if (isEmpty(value)) return defaultValue;
            if (value.equals("1")) return true;
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0)
            return true;
        return false;
    }
}
