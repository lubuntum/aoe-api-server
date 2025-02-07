package com.englishaoe.lesson.services.payment;

import com.englishaoe.lesson.database.entity.transactions.Payment;
import com.englishaoe.lesson.database.entity.transactions.PaymentStatusEnum;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.PaymentService;
import com.englishaoe.lesson.dto.payment.PaymentWebhookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HandlePaymentWebhook {
    @Autowired
    PaymentService paymentService;
    @Autowired
    CustomerServices customerServices;
    public void handle(PaymentWebhookRequest request){
        try{
            Payment payment = paymentService.getPaymentByPaymentId(request.getData().getId());
            if (payment.getStatus().equals(PaymentStatusEnum.SUCCEEDED.getStatus())) return;
            if (!request.getData().getStatus().equals(PaymentStatusEnum.SUCCEEDED.getStatus())){
                payment.setStatus(request.getData().getStatus());
                paymentService.save(payment);
                return;
            }

            payment.setStatus(request.getData().getStatus());
            paymentService.save(payment);
            customerServices.addBalanceToCustomerById(payment.getCustomerId(), payment.getValue());
        } catch (Exception e) {
            throw new RuntimeException("Error with received webhook: " + e.getMessage());
        }


    }
}
