package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.services.TestVariantsGeneratorServices;
import com.englishaoe.lesson.dto.account.CustomerAccountDTO;
import com.englishaoe.lesson.dto.account.AccountMapper;
import com.englishaoe.lesson.dto.account.CustomerHeaderDTO;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**TODO
 * 1. ????? ????????? ???????? ??? ??, ? variants, ??????? ???? ?? sqlite ????? ???????*/
@RestController
@RequestMapping("/api/account")
public class AccountController {
    //TEST GENERATORS
    @Autowired
    TestVariantsGeneratorServices variantsGenerator;
    //TEST GENERATORS
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AccountMapper accountMapper;

    //@PostMapping("/completed-variants")
    //public List<>
    @GetMapping("/customer")
    public ResponseEntity<CustomerAccountDTO> customerAccountData(@RequestHeader("Authorization") String token){
        //TODO
        // 1. grab Customer id from token, then find him in database...
        // 2. translate Customer + actual date of subscription buying to CustomerAccountDTO and send it
        String extractedIdTest = jwtUtil.extractSubject(token);
        Customer testCustomer = new Customer(Long.valueOf(jwtUtil.extractSubject(token)),
                "Pavel", "Pavel@gmail.com", "hashPass",
                "Павел","Большаков","25.09.2002", 12,1 );

        //return DTO
        return ResponseEntity.ok(accountMapper.customerToDTO(testCustomer));
    }
    @GetMapping("/header")
    public ResponseEntity<CustomerHeaderDTO> headerData(@RequestHeader("Authorization") String token) {
        // 1. grab Customer id from token, then find him in database...
        Customer testCustomer = new Customer(Long.valueOf(jwtUtil.extractSubject(token)),
                "Pavel", "Pavel@gmail.com", "hashPass",
                "Павел","Большаков","25.09.2002", 12,1 );
        return ResponseEntity.ok(accountMapper.customerToHeaderDTO(testCustomer));
    }
}
