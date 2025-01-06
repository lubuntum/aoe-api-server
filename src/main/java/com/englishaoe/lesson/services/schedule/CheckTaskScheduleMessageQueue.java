package com.englishaoe.lesson.services.schedule;

import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.taskcheck.TaskCheckerFactory;
import com.englishaoe.lesson.taskcheck.checkers.TaskTypeConverter;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
@Component
public class CheckTaskScheduleMessageQueue {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Semaphore semaphore = new Semaphore(1);
    private final CustomerTaskService customerTaskService;
    private final TaskCheckerFactory taskCheckerFactory;
    public CheckTaskScheduleMessageQueue(CustomerTaskService customerTaskService, TaskCheckerFactory taskCheckerFactory){
        this.customerTaskService = customerTaskService;
        this.taskCheckerFactory = taskCheckerFactory;
        startTask();
    }
    public void startTask(){
        scheduler.scheduleWithFixedDelay(this::performTask, 0, 35, TimeUnit.SECONDS);
    }
    public void performTask() {
        if (!semaphore.tryAcquire()){
            System.out.print("Message skipped due to concurrency limit");
        }
        try{
            CustomerTask customerTask = customerTaskService.getOldestCustomerTaskByStatus(CheckStatusEnum.TRANSCRIBED.getStatus());
            if (customerTask == null) return;
            customerTaskService.updateCheckStatus(
                    customerTask.getId(),
                    CheckStatusEnum.CHECKING.getStatus(),
                    TaskResultTypeEnum.EXPRESS.getTaskResultType());
            CustomerTaskDTO customerTaskDTO =
                    new Gson().fromJson(customerTask.getTempCheckingData(), CustomerTaskDTO.class);
            customerTaskDTO.setTranscribateText(customerTask.getAnswer());
            customerTaskDTO.getTask().parseTaskContent();
            TaskChecker taskChecker =
                    taskCheckerFactory.getService(
                            TaskTypeConverter.serviceNameFromTaskType(
                                    customerTaskDTO.getTask().getTaskType()));
            taskChecker.checkTask(customerTaskDTO);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

}
