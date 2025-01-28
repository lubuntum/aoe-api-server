package com.englishaoe.lesson.utility;

import java.security.SecureRandom;

public class PromocodeUtil {
    private static final String CHARACTERS =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String PREFIX = "TME_";
    private static final SecureRandom RANDOM = new SecureRandom();
    public static String generatePromocode(int length){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < length; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        result.insert(0, PREFIX);
        return result.toString();
    }
}
