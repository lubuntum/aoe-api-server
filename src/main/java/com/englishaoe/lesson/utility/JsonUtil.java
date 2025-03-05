package com.englishaoe.lesson.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {
    public static final String jsonRegex = "\\{.*\\}";//remove all \n
    public static String extractJson(String message) {
        Pattern pattern = Pattern.compile(jsonRegex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find())
            return matcher.group(0);
        return null;
    }
    /**
     * @param json string with data
     * @param key for value searching
     * return value by key from json (json and returned value represented as string)
     * */
    public static String getValueByKeyFromJson(String json, String key) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject.get(key).getAsString();
    }
    public static String getValueByKeyFromJsonArray(String data, String key) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(data);
        if (!jsonElement.isJsonArray()) return null;
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for(JsonElement element : jsonArray) {
            if (!element.isJsonObject()) continue;
            JsonObject jsonObject = element.getAsJsonObject();
            if(jsonObject.has(key)) return jsonObject.get(key).getAsString();

            if (!jsonObject.has("taskContent")) continue;
            String taskContent = jsonObject.get("taskContent").getAsString();
            JsonElement taskContentElement = parser.parse(taskContent);
            if (!taskContentElement.isJsonObject()) continue;
            JsonObject taskContentObject = taskContentElement.getAsJsonObject();
            if (taskContentObject.has(key)) return taskContentObject.get(key).getAsString();
        }
        return null;
    }
    /**
     * @param json string with data
     * @param key array's key
     * return List<String> for searching array by key
     * */
    public static List<String> getArrayByKeyFromJson(String json, String key) {
        try {
            JsonParser jsonParser = new JsonParser();

            JsonElement jsonElement = jsonParser.parse(json);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            JsonArray jsonArray = jsonObject.getAsJsonArray(key);
            List<String> list = new LinkedList<>();
            for(int i = 0; i < jsonArray.size();i++)
                list.add(jsonArray.get(i).getAsString());
            return list;
        } catch (Exception e) {
            System.err.println("Can't found " + key + " from " + json);
            return null;
        }
    }
}
