package com.webwalker.core.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON序列化辅助类
 **/
public class JsonUtil {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object obj) {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        try {
            return gson.fromJson(json, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toList(String str, TypeToken<List<String>> type) {
        ArrayList<T> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        try {
            JsonArray jarray = parser.parse(str).getAsJsonArray();
            for (JsonElement obj : jarray) {
                T item = gson.fromJson(obj, type.getType());
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
