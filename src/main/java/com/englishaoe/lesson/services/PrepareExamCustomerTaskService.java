package com.englishaoe.lesson.services;

import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.VariantService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * */
@Service
public class PrepareExamCustomerTaskService {
    @Autowired
    CustomerTaskService customerTaskService;
    @Autowired
    CustomerTaskDTOAssembleService customerTaskDTOAssembleService;

    public void prepareCustomerTasks(Long examId){
        List<CustomerTask> customerTaskList = customerTaskService.getCustomerTasksByExamId(examId);
        Gson gson = new Gson();
        for(CustomerTask cT : customerTaskList) {
            cT.setTempCheckingData(gson.toJson(customerTaskDTOAssembleService.assemble(cT)));
            customerTaskService.updateCheckStatus(
                    cT.getId(),
                    CheckStatusEnum.UNTRANSCRIBED.getStatus(),
                    TaskResultTypeEnum.EXPRESS.getTaskResultType());
        }
    }
}
