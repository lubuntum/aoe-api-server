package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.transactions.SubscriptionTransaction;
import com.englishaoe.lesson.database.entity.transactions.SubscriptionType;
import com.englishaoe.lesson.database.repository.SubscriptionTransactionRepository;
import com.englishaoe.lesson.database.repository.SubscriptionTypeRepository;
import com.englishaoe.lesson.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class SubscriptionTransactionService {
    @Autowired
    SubscriptionTransactionRepository subscriptionTransactionRepository;
    @Autowired
    SubscriptionTypeRepository subscriptionTypeRepository;
    @Autowired
    CustomerServices customerServices;
    @Transactional
    public boolean purchaseSubscriptionForCustomer(Long customerId, Long subscriptionTypeId){
        SubscriptionType subscriptionType = subscriptionTypeRepository.
                findById(subscriptionTypeId).orElseThrow(()-> new IllegalArgumentException("No such subscription plan"));
        if (!subscriptionType.getIsValid())
            throw new IllegalArgumentException("Picked subscription type is no more valid");

        if (!customerServices.subBalanceToCustomerById(customerId, subscriptionType.getPrice()))
            throw new IllegalArgumentException("Not enough balance");

        SubscriptionTransaction subTransaction = new SubscriptionTransaction();
        subTransaction.setCustomerId(customerId);
        subTransaction.setSubscriptionTypeId(subscriptionTypeId);
        subTransaction.setAmountPaid(subscriptionType.getPrice());
        subTransaction.setTransactionDate(DateUtil.getCurrentDate());
        //TODO add not month but month * 30 days
        subTransaction.setExpireDate(DateUtil.getExpireDate(subscriptionType.getMonthsCount()));
        subscriptionTransactionRepository.save(subTransaction);

        customerServices.updateCustomerSubscriptionInfo(
                DateUtil.getCurrentDate(),
                DateUtil.getExpireDate(subscriptionType.getMonthsCount()),
                customerId);
        return true;
    }
}
