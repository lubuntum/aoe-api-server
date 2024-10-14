package com.englishaoe.lesson.database.entity.transactions;

import com.englishaoe.lesson.database.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription_transaction")
public class SubscriptionTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")//change to subscription_id
    private Subscription subscription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")//change to customer_id
    private Customer customer;
    @Column(name = "transaction_date")
    private String transactionDate;

}
