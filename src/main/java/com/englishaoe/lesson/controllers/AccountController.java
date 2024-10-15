package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.repository.CustomerRepository;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.dto.account.CustomerAccountDTO;
import com.englishaoe.lesson.dto.account.AccountMapper;
import com.englishaoe.lesson.dto.account.CustomerHeaderDTO;
import com.englishaoe.lesson.exceptions.jwtkeys.JwtExpiredException;
import com.englishaoe.lesson.utility.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AccountMapper accountMapper;
    //repository services
    @Autowired
    CustomerServices customerServices;
    //TODO add some method or filter for some routes which requier authentification like /customer, /header
    //@PostMapping("/completed-variants")
    //public List<>
    @GetMapping("/customer")
    public ResponseEntity<CustomerAccountDTO> customerAccountData(@RequestHeader("Authorization") String token){
        //TODO
        // 1. grab Customer id from token, then find him in resrives DTO...
        // 2. validate by token before
        CustomerAccountDTO customerAccountDTO = customerServices.getCustomerAccountDataById(Long.valueOf(jwtUtil.extractSubject(token)));
        //return DTO
        return ResponseEntity.ok(customerAccountDTO);
    }
    @GetMapping("/header")
    public ResponseEntity<CustomerHeaderDTO> headerData(@RequestHeader("Authorization") String token) {
        try {
            //TODO
            // 1. grab Customer id from token, then find him in database repository DTO...
            CustomerHeaderDTO customerHeaderDTO = customerServices.getCustomerHeaderDataById(Long.valueOf(jwtUtil.extractSubject(token)));
            return ResponseEntity.ok(customerHeaderDTO);
        }
        catch (ExpiredJwtException e) {
            throw new JwtExpiredException("JWT token is expired", e);
        }
    }
    @GetMapping("/customer-by-id")
    public ResponseEntity<CustomerAccountDTO> customerTest(@RequestHeader("Authorization") String token ){
        CustomerAccountDTO customerAccountDTO = customerServices.getCustomerAccountDataById(Long.valueOf(jwtUtil.extractSubject(token)));
        return ResponseEntity.ok(customerAccountDTO);
    }
}
