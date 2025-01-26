package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.repository.PartnerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerTypeService {
    @Autowired
    PartnerTypeRepository partnerTypeRepository;

    public Long getPartnerTypeIdByType(String type){
        return partnerTypeRepository.findPartnerTypeIdByType(type);
    }
}
