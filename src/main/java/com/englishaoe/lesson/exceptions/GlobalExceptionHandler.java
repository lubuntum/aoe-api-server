package com.englishaoe.lesson.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RegularException.class)
    public ResponseEntity<Map<String, Object>> handleRegularException(RegularException e){
        Map<String, Object> response = new HashMap<>();
        response.put("error", e.getMessage());
        response.put("status", e.getStatusCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getStatusCode()));
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token is expired: " + e.getMessage());
    }
}
