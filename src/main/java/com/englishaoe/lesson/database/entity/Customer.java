package com.englishaoe.lesson.database.entity;

import com.englishaoe.lesson.database.entity.partnership.PromocodeUsage;
import com.englishaoe.lesson.database.entity.role.Role;
import com.englishaoe.lesson.database.entity.transactions.SubscriptionTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "registration_date")
    private String registrationDate;
    @Column(name = "current_balance", precision = 10, scale = 2)
    private BigDecimal currentBalance;
    @Column(name = "purchase_sub_date")
    private String purchaseSubDate;
    @Column(name = "expire_sub_date")
    private String expireSubDate;
    @Column(name = "patronymic")
    private String patronymic;

    @ManyToMany
    @JoinTable(
            name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY ,orphanRemoval = true)
    private List<SubscriptionTransaction> subscriptionTransactions = new LinkedList<>();
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PromocodeUsage> promocodeUsageList = new LinkedList<>();
}
