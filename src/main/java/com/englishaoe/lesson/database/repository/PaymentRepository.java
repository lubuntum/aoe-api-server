package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.transactions.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStatusAndCustomerId(String status, Long customerId);
    Payment findPaymentByPaymentId(String paymentId);
}
