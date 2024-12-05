package com.englishaoe.lesson.taskcheck;

import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.database.services.TaskResultTypeService;
import com.englishaoe.lesson.dto.results.CheckDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.utility.DateUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultCollect {
    @Autowired
    public TaskResultTypeService taskResultTypeService;
    /**
     * customerTaskDTO - entity where store user complete task data
     * CheckDTP - result of checking like grade, date and etc, send from AI or just locally.
     * */
    public TaskResult collect(CustomerTaskDTO customerTaskDTO, CheckDTO checkDTO){
        TaskResult taskResult = new TaskResult();
        Gson gson = new Gson();
        taskResult.setResult(gson.toJson(checkDTO));
        taskResult.setCustomerTaskId(customerTaskDTO.getId());
        taskResult.setTaskResultType(taskResultTypeService.getTaskResultTypeByType("express"));
        taskResult.setSendDate(DateUtil.getCurrentDate());

        return taskResult;
    }
}
