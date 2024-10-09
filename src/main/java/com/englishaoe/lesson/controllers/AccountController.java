package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.services.TestVariantsGeneratorServices;
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

    //@PostMapping("/completed-variants")
    //public List<>
    @GetMapping("/customer")
    public ResponseEntity<Customer> customerAccountData(@RequestHeader("Authorization") String token){
        //TODO grab Customer id from token, then find him in database...
        String extractedIdTest = jwtUtil.extractSubject(token);
        Customer customer = new Customer(Long.valueOf(jwtUtil.extractSubject(token)),
                "Pavel", "Pavel@gmail.com", "hashPass",
                "Павел","Большаков","25.09.2002", 8,1 );
        return ResponseEntity.ok(customer);
    }
}
