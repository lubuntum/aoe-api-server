package com.englishaoe.lesson.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Convert any value to json
 * */
public class ParseUtil {
    public static <T> String serialize(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
    public static <T> T deserialize(String json, TypeToken<T> typeToken) {
        Gson gson = new Gson();
        return gson.fromJson(json, typeToken.getType());
    }

}
