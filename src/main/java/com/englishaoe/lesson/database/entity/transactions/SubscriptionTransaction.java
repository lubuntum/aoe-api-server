package com.englishaoe.lesson.database.entity.transactions;

import com.englishaoe.lesson.database.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription_transaction")
public class SubscriptionTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "subscription_type_id")
    private Long subscriptionTypeId;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "transaction_date")
    private String transactionDate;
    @Column(name = "expire_date")
    private String expireDate;
    @Column(name = "amount_paid")
    private BigDecimal amountPaid;
}
