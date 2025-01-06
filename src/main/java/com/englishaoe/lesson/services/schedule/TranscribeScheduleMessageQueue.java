package com.englishaoe.lesson.services.schedule;

import com.englishaoe.lesson.api.transcribe.APITranscribe;
import com.englishaoe.lesson.api.transcribe.TranscribeFactory;
import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
@Component
public class TranscribeScheduleMessageQueue {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Semaphore semaphore = new Semaphore(1);
    private final CustomerTaskService customerTaskService;
    private final TranscribeFactory transcribeFactory;
    public TranscribeScheduleMessageQueue(CustomerTaskService customerTaskService, TranscribeFactory transcribeFactory){
        this.customerTaskService = customerTaskService;
        this.transcribeFactory = transcribeFactory;
        startTask();
    }
    public void startTask(){
        scheduler.scheduleWithFixedDelay(this::performTask, 0,5, TimeUnit.SECONDS);
    }
    /*TODO
    *  also after receiving response from transcrip API check status code
    *  if something wrong depend on status perform checkStatus changing
    *  or increase delay between queue calls*/
    public void performTask() {
        if (!semaphore.tryAcquire()) {
            System.out.print("Message skipped due to concurrency limit");
        }
        try{
            CustomerTask customerTask = customerTaskService.getOldestCustomerTaskByStatus(CheckStatusEnum.UNTRANSCRIBED.getStatus());
            if(customerTask == null) return;
            CustomerTaskDTO customerTaskDTO =
                    new Gson().fromJson(customerTask.getTempCheckingData(), CustomerTaskDTO.class);
            APITranscribe apiTranscribe = transcribeFactory.getService(customerTaskDTO.getTranscriptionServiceName());
            String transcribeAnswer = apiTranscribe.transcribe(customerTaskDTO.getAudioPath());
            customerTaskService.updateAnswerInCustomerTask(customerTaskDTO.getId(), transcribeAnswer);
            customerTaskService.updateCheckStatus(customerTask.getId(), CheckStatusEnum.TRANSCRIBED.getStatus(), TaskResultTypeEnum.EXPRESS.getTaskResultType());
        } catch (Exception e) {
            System.err.print(e.getMessage());
        } finally {
            semaphore.release();
        }
    }
}
