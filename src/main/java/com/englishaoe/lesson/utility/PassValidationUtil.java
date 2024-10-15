package com.englishaoe.lesson.utility;

import com.englishaoe.lesson.exceptions.RegularException;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PassValidationUtil {
    public boolean validatePassword(String password, String hash) {
        String hashedPassword = hashPassword(password);
        return hashedPassword.equals(hash);
    }
    public String hashPassword(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for(byte b : hash){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
