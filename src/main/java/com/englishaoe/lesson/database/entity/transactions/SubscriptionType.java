package com.englishaoe.lesson.database.entity.transactions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription_type")
public class SubscriptionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "months_count")
    private Integer monthsCount;
    @Column(name = "is_valid")
    private Boolean isValid;
}
