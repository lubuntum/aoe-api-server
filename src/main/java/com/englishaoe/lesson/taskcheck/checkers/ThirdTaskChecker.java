package com.englishaoe.lesson.taskcheck.checkers;

import com.englishaoe.lesson.api.ai.AIService;
import com.englishaoe.lesson.api.ai.AIServicesFactory;
import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.TaskResultService;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.dto.results.CheckDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.taskcheck.PromptBuilder;
import com.englishaoe.lesson.taskcheck.ResultCollect;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("third")
public class ThirdTaskChecker extends BaseAITaskChecker {
    @Autowired
    private TaskTypeService taskTypeService;

    @Override
    public String build(CustomerTaskDTO customerTaskDTO) {
        String prompt = taskTypeService.getPromptByType(customerTaskDTO.getTask().getTaskType());
        List<String> questions = customerTaskDTO.getTask().getTaskContentDTO().getQuestions();
        return String.format(prompt, customerTaskDTO.getTask().getTaskContentDTO().getTaskGuide(),
                questions.get(0),
                questions.get(1),
                questions.get(2),
                questions.get(3),
                questions.get(4),
                customerTaskDTO.getTranscribateText());
    }
}
