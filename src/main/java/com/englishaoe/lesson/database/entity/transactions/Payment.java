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
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "payment_id")
    String paymentId;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "payment_date")
    private String paymentDate;
    @Column(name = "value")
    private BigDecimal value;
    @Column(name = "currency")
    private String currency;
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, updatable = false, insertable = false)
    private Customer customer;
}
