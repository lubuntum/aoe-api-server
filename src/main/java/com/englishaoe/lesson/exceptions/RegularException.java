package com.englishaoe.lesson.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class RegularException extends RuntimeException{
    private final String message;
    private final int statusCode;

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
