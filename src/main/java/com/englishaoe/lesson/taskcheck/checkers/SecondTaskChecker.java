package com.englishaoe.lesson.taskcheck.checkers;

import com.englishaoe.lesson.api.ai.AIService;
import com.englishaoe.lesson.api.ai.AIServicesFactory;
import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.TaskResultService;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.dto.results.CheckDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.taskcheck.PromptBuilder;
import com.englishaoe.lesson.taskcheck.ResultCollect;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.utility.TextUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("second")
public class SecondTaskChecker extends BaseAITaskChecker {
    @Autowired
    private TaskTypeService taskTypeService;

    public String build(CustomerTaskDTO customerTaskDTO) {
        String prompt = taskTypeService.getPromptByType(customerTaskDTO.getTask().getTaskType());
        List<String> topics = customerTaskDTO.getTask().getTaskContentDTO().getTopics();
        return String.format(prompt,
                customerTaskDTO.getTask().getTaskContentDTO().getTaskText(),
                topics.get(0),
                topics.get(1),
                topics.get(2),
                topics.get(3),
                customerTaskDTO.getTranscribateText());
    }
}
