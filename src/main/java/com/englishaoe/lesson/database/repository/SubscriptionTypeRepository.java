package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.transactions.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long> {
    List<SubscriptionType> findByIsValidTrue();
}
