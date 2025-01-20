package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.transactions.SubscriptionTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionTransactionRepository extends JpaRepository<SubscriptionTransaction, Long> {
}
