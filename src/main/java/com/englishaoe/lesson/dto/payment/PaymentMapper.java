package com.englishaoe.lesson.dto.payment;

import com.englishaoe.lesson.database.entity.transactions.Payment;

public class PaymentMapper {
    public static Payment fromDTO(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setPaymentId(dto.getPaymentId());
        payment.setValue(dto.getAmount().getValue());
        payment.setCurrency(dto.getAmount().getCurrency());
        payment.setStatus(dto.getStatus());
        payment.setPaymentDate(dto.getCreatedAt());
        return payment;
    }
}
