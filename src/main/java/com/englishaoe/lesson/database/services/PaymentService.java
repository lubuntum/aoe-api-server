package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.transactions.Payment;
import com.englishaoe.lesson.database.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void save(Payment payment){
        paymentRepository.save(payment);
    }
    public List<Payment> getPaymentsByStatusAndCustomerId(String status, Long customerId){
        return paymentRepository.findByStatusAndCustomerId(status, customerId);
    }
    public Payment getPaymentByPaymentId(String paymentId){
        return paymentRepository.findPaymentByPaymentId(paymentId);
    }
}
