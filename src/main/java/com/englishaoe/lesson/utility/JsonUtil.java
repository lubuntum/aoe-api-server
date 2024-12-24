package com.englishaoe.lesson.utility;

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
}
