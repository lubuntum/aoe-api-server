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
import com.englishaoe.lesson.services.transactions.TasksCheckingTransactionServices;
import com.englishaoe.lesson.taskcheck.PromptBuilder;
import com.englishaoe.lesson.taskcheck.ResultCollect;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.utility.JsonUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class BaseAITaskChecker implements TaskChecker, PromptBuilder {
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private AIServicesFactory aiServicesFactory;
    @Autowired
    private ResultCollect resultCollect;
    @Autowired
    private TaskResultService taskResultService;
    @Autowired
    private CustomerTaskService customerTaskService;
    @Autowired
    private TasksCheckingTransactionServices tasksCheckingTransactionServices;
    @Transactional
    @Async
    @Override
    public void checkTask(CustomerTaskDTO customerTaskDTO) {
        try{
            Gson gson = new Gson();
            String prompt = build(customerTaskDTO);
            AIService aiService = aiServicesFactory.getService(customerTaskDTO.getAiServiceName());
            String jsonResponseContent = JsonUtil.extractJson(
                    aiService.sendRequest(customerTaskDTO, prompt).replaceAll("\n",""));
            validateResponse(jsonResponseContent);
            CheckDTO checkDTO = gson.fromJson(jsonResponseContent, CheckDTO.class);
            validateCheckDTO(checkDTO);
            taskResultService.saveTaskResult(resultCollect.collect(customerTaskDTO, checkDTO));
            //TODO if customerTask has examId, then add grade to express_total_grade
            //update status
            customerTaskService.updateCheckStatus(
                    customerTaskDTO.getId(),
                    CheckStatusEnum.COMPLETED.getStatus(),
                    TaskResultTypeEnum.EXPRESS.getTaskResultType());
            customerTaskService.updateTempCheckingData(customerTaskDTO.getId(), null);
        } catch (Exception e) {
            //TODO save error massage
            tasksCheckingTransactionServices.refund(
                    customerTaskDTO.getCustomerId(),
                    taskTypeService.getPriceByTaskType(customerTaskDTO.getTask().getTaskType()));
            customerTaskService.updateCheckStatus(
                    customerTaskDTO.getId(),
                    CheckStatusEnum.INCOMPLETE.getStatus(),
                    TaskResultTypeEnum.EXPRESS.getTaskResultType());
        }
    }
    private void validateResponse(String response) {
        if (response == null || response.isBlank())
            throw new IllegalArgumentException("Response is null or blank");
    }
    private void validateCheckDTO(CheckDTO checkDTO) {
        if (checkDTO == null) throw new NullPointerException();
    }

    @Override
    public abstract String build(CustomerTaskDTO customerTaskDTO);
}
