package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.results.CheckStatus;
import com.englishaoe.lesson.database.repository.CheckStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckStatusService {
    @Autowired
    CheckStatusRepository checkStatusRepository;
    public CheckStatus getIdByStatus(String status){
        return checkStatusRepository.findByStatus(status);
    }
}
