package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.transactions.Payment;
import com.englishaoe.lesson.database.entity.transactions.PaymentStatusEnum;
import com.englishaoe.lesson.database.repository.PaymentRepository;
import com.englishaoe.lesson.dto.payment.PaymentRequest;
import com.englishaoe.lesson.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public Long createDefaultPayment(PaymentRequest paymentRequest, Long customerId){
        Payment payment = new Payment();
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setValue(BigDecimal.valueOf(paymentRequest.getAmount()));
        payment.setPaymentDate(DateUtil.getCurrentDate());
        payment.setStatus(PaymentStatusEnum.PENDING.getStatus());
        payment.setCustomerId(customerId);
        return paymentRepository.save(payment).getId();
    }
}
