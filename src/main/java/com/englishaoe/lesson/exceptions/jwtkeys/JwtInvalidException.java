package com.englishaoe.lesson.exceptions.jwtkeys;

public class JwtInvalidException extends RuntimeException{
    public JwtInvalidException(String message) {
        super(message);
    }
    public JwtInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
