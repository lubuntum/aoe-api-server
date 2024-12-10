package com.englishaoe.lesson.utility;

public class TextUtil {
    public static String getClearText(String text) {
        if (text == null) return "";
        return text.replaceAll("[^a-zA-Z0-9\\s]","").toLowerCase();
    }
}
