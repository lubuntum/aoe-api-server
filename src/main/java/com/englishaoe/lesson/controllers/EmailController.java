package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import com.englishaoe.lesson.dto.email.EmailRequest;
import com.englishaoe.lesson.services.email.EmailService;
import com.englishaoe.lesson.utility.JwtUtil;
import com.englishaoe.lesson.utility.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            emailService.sendMessage(
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
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPasswordByEmail(@RequestBody CustomerAuthDTO customerAuthDTO) {
        try {
            Customer customer = customerServices.getCustomerByEmail(customerAuthDTO.getEmail());
            if (customer == null)
                return ResponseEntity.ok("Message was send to you email account");//For safety purposes
            customerAuthDTO.setId(customer.getId());
            emailService.sendPageMessage(
                    customer.getEmail(),
                    "Password reset TestMyEng.ru",
                    emailService.assemblyEmailResetPasswordText(
                            customer.getName(),
                            customer.getEmail(),
                            jwtUtil.generateToken(String.valueOf(ParseUtil.serialize(customerAuthDTO)))
                    ));
            return ResponseEntity.ok("Message was send to you email account");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
