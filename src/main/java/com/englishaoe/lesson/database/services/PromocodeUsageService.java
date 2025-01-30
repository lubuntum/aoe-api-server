package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.repository.PromocodeUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromocodeUsageService {
    @Autowired
    private PromocodeUsageRepository promocodeUsageRepository;

    public Integer getPromocodeUsageCountByPartnerId(Long partnerId){
        return promocodeUsageRepository.countByPartnerId(partnerId);
    }
}
