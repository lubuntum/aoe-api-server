package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.transactions.SubscriptionType;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.SubscriptionTransactionService;
import com.englishaoe.lesson.database.services.SubscriptionTypeService;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    SubscriptionTypeService subscriptionTypeService;
    @Autowired
    SubscriptionTransactionService subscriptionTransactionService;
    @Autowired
    CustomerServices customerServices;
    @GetMapping("/valid-subscriptions")
    public ResponseEntity<List<SubscriptionType>> validSubscriptions(){
        return ResponseEntity.ok(subscriptionTypeService.getAllValidSubscriptionType());
    }
    @PostMapping("/purchase-subscription")
    public ResponseEntity<Boolean> purchaseSubscription(@RequestHeader("Authorization") String token,
                                                       @RequestParam("subscriptionTypeId")Long subscriptionTypeId){
        return ResponseEntity.ok(
                subscriptionTransactionService.purchaseSubscriptionForCustomer(
                    Long.valueOf(jwtUtil.extractSubject(token)),subscriptionTypeId));
    }
    @GetMapping("/check-subscription")
    public ResponseEntity<Boolean> checkSubscription(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(
                    customerServices.isCustomerSubscribed(Long.valueOf(jwtUtil.extractSubject(token))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        }
    }
}
