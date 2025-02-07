package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.transactions.PaymentStatusEnum;
import com.englishaoe.lesson.dto.payment.PaymentRequest;
import com.englishaoe.lesson.dto.payment.PaymentWebhookRequest;
import com.englishaoe.lesson.services.payment.HandlePaymentStatusService;
import com.englishaoe.lesson.services.payment.HandlePaymentWebhook;
import com.englishaoe.lesson.services.payment.PaymentServiceYooKassa;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PaymentServiceYooKassa paymentServiceYookassa;
    @Autowired
    HandlePaymentStatusService handlePaymentStatusService;
    @Autowired
    HandlePaymentWebhook handlePaymentWebhook;

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestHeader("Authorization") String token,
                                          @RequestBody PaymentRequest paymentRequest){
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(paymentServiceYookassa.createPayment(paymentRequest, Long.valueOf(jwtUtil.extractSubject(token))));

        } catch (RuntimeException exception){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating payment " + exception.getMessage());
        }

    }
    @GetMapping("status")
    public ResponseEntity<String> checkPaymentStatus(@RequestHeader("Authorization") String token){
        handlePaymentStatusService.checkPaymentsStatusForCustomer(PaymentStatusEnum.PENDING.getStatus(), Long.valueOf(jwtUtil.extractSubject(token)));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("webhook/yookassa")
    public ResponseEntity<String> handleYooKassaWebhook(@RequestBody PaymentWebhookRequest paymentWebhookRequest) {
        handlePaymentWebhook.handle(paymentWebhookRequest);
        return new ResponseEntity<>("received", HttpStatus.OK);
    }
}
