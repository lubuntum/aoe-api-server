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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Component("fourth")
public class FourthTaskChecker extends BaseAITaskChecker {
    @Autowired
    private TaskTypeService taskTypeService;
    @Override
    public String build(CustomerTaskDTO customerTaskDTO) {
        String prompt = taskTypeService.getPromptByType(customerTaskDTO.getTask().getTaskType());
        List<String> subTasks = customerTaskDTO.getTask().getTaskContentDTO().getSubTasks();
        return String.format(prompt, customerTaskDTO.getTask().getTaskContentDTO().getTaskGuide(),
                subTasks.get(0),
                subTasks.get(1),
                subTasks.get(2),
                subTasks.get(3),
                customerTaskDTO.getTranscribateText());
    }


}
