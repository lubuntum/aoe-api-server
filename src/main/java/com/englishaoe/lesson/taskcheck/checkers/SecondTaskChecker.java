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

//TODO taskText, topic 1..4, answer
@Service("second")
public class SecondTaskChecker implements TaskChecker, PromptBuilder {
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    CustomerTaskService customerTaskService;
    @Autowired
    TaskResultService taskResultService;
    @Autowired
    private AIServicesFactory aiServicesFactory;
    @Autowired
    ResultCollect resultCollect;
    @Transactional
    @Async
    @Override
    public void checkTask(CustomerTaskDTO customerTaskDTO) {
        Gson gson = new Gson();
        String prompt = build(customerTaskDTO);
        AIService aiService = aiServicesFactory.getService(customerTaskDTO.getAiServiceName());
        String jsonResponseContent = aiService.sendRequest(customerTaskDTO, prompt);
        //TODO write wrapper for jsonResponseContent which delete all except json data {...}
        CheckDTO checkDTO = gson.fromJson(jsonResponseContent, CheckDTO.class);
        taskResultService.saveTaskResult(resultCollect.collect(customerTaskDTO, checkDTO));
        customerTaskService.updateCheckStatus(
                customerTaskDTO.getId(),
                CheckStatusEnum.COMPLETED.getStatus(),
                TaskResultTypeEnum.EXPRESS.getTaskResultType());
    }
    public String build(CustomerTaskDTO customerTaskDTO) {
        String prompt = taskTypeService.getPromptByType(customerTaskDTO.getTask().getTaskType());
        List<String> topics = customerTaskDTO.getTask().getTaskContentDTO().getTopics();
        return String.format(prompt,
                customerTaskDTO.getTask().getTaskContentDTO().getTaskText().get(0),
                topics.get(0),
                topics.get(1),
                topics.get(2),
                topics.get(3),
                customerTaskDTO.getTranscribateText());
    }
}
