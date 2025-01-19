package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.transactions.SubscriptionType;
import com.englishaoe.lesson.database.repository.SubscriptionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubscriptionTypeService {
    @Autowired
    SubscriptionTypeRepository subscriptionTypeRepository;
    public List<SubscriptionType> getAllValidSubscriptionType(){
        return subscriptionTypeRepository.findByIsValidTrue();
    }
}
