package com.englishaoe.lesson.services.schedule;

import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.TaskResultService;
import com.englishaoe.lesson.dto.results.CheckDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.taskcheck.TaskCheckEndHandler;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.taskcheck.TaskCheckerFactory;
import com.englishaoe.lesson.taskcheck.checkers.TaskTypeConverter;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
@Component
public class CheckTaskScheduleMessageQueue {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Semaphore semaphore = new Semaphore(1);
    private final CustomerTaskService customerTaskService;
    private final TaskResultService taskResultService;
    private final TaskCheckerFactory taskCheckerFactory;
    private final TaskCheckEndHandler taskCheckEndHandler;
    public CheckTaskScheduleMessageQueue(CustomerTaskService customerTaskService,
                                         TaskCheckerFactory taskCheckerFactory,
                                         TaskCheckEndHandler taskCheckEndHandler,
                                         TaskResultService taskResultService){
        this.customerTaskService = customerTaskService;
        this.taskCheckerFactory = taskCheckerFactory;
        this.taskCheckEndHandler = taskCheckEndHandler;
        this.taskResultService = taskResultService;
        startTask();
    }
    public void startTask(){
        scheduler.scheduleWithFixedDelay(this::performTask, 0, 35, TimeUnit.SECONDS);
    }
    public void performTask() {
        if (!semaphore.tryAcquire()){
            System.out.print("Message skipped due to concurrency limit");
        }
        CustomerTask customerTask = customerTaskService.getOldestCustomerTaskByStatus(CheckStatusEnum.TRANSCRIBED.getStatus());
        if (customerTask == null) return;
        CustomerTaskDTO customerTaskDTO =
                new Gson().fromJson(customerTask.getTempCheckingData(), CustomerTaskDTO.class);
        customerTaskDTO.setTranscribateText(customerTask.getAnswer());
        customerTaskDTO.getTask().parseTaskContent();
        //if exam was already canceled by some error, but this task was checked previously
        List<TaskResult> taskResults = taskResultService.getTaskResultByCustomerTaskId(customerTaskDTO.getId());
        if (taskResults != null && !taskResults.isEmpty()){
            CheckDTO checkDTO = new Gson().fromJson(taskResults.get(0).getResult(), CheckDTO.class);
            taskCheckEndHandler.completeChecking(customerTaskDTO, checkDTO);
            return;
        }
        try{
            customerTaskService.updateCheckStatus(
                    customerTask.getId(),
                    CheckStatusEnum.CHECKING.getStatus(),
                    TaskResultTypeEnum.EXPRESS.getTaskResultType());
            TaskChecker taskChecker =
                    taskCheckerFactory.getService(
                            TaskTypeConverter.serviceNameFromTaskType(
                                    customerTaskDTO.getTask().getTaskType()));
            taskChecker.checkTask(customerTaskDTO);
        } catch (IllegalAccessException e) {
            taskCheckEndHandler.failTaskCheck(customerTaskDTO);
        } finally {
            semaphore.release();
        }
    }

}
