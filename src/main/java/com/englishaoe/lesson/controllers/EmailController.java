package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.dto.email.EmailRequest;
import com.englishaoe.lesson.services.email.EmailService;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    EmailService emailService;
    @Autowired
    CustomerServices customerServices;
    @Autowired
    JwtUtil jwtUtil;
    /**
     * For test purposes, remove later
     * */
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendRegistrationMessage(
                    emailRequest.getTo(),
                    emailRequest.getSubject(),
                    emailRequest.getText());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
    /**
     * Call it when user go to sent link from email account
     * */
    @GetMapping("/confirm")
    public ResponseEntity<Boolean> confirmEmail(@RequestHeader("Authorization") String token){
        try {
            return ResponseEntity.ok(customerServices.confirmCustomerEmail(Long.valueOf(jwtUtil.extractSubject(token))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
