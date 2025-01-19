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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")//change to customer_id
    private Customer customer;
    @Column(name = "transaction_date")
    private String transactionDate;
    @Column(name = "expire_date")
    private String expireDate;
    @Column(name = "amount_paid")
    private BigDecimal amountPaid;


}
