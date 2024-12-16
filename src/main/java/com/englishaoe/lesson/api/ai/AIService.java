package com.englishaoe.lesson.api.ai;

import com.englishaoe.lesson.dto.results.CustomerTaskDTO;

public interface AIService {
    String sendRequest(CustomerTaskDTO customerTaskDTO, String prompt);
}
