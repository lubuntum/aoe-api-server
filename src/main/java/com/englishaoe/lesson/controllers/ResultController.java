package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.api.transcribe.APITranscribe;
import com.englishaoe.lesson.api.transcribe.TranscribeFactory;
import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.ExamService;
import com.englishaoe.lesson.database.services.TaskResultService;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.services.CustomerTaskDTOAssembleService;
import com.englishaoe.lesson.services.PrepareExamCustomerTaskService;
import com.englishaoe.lesson.services.transactions.TasksCheckingTransactionServices;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.taskcheck.TaskCheckerFactory;
import com.englishaoe.lesson.taskcheck.checkers.TaskTypeConverter;
import com.englishaoe.lesson.utility.JwtUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/result")
public class ResultController {
    // factories for pick services by name in request
    @Autowired
    private TranscribeFactory transcribeFactory;
    @Autowired
    private TaskCheckerFactory taskCheckerFactory;
    @Autowired
    PrepareExamCustomerTaskService prepareExamCustomerTaskService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomerTaskService customerTaskService;
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private TasksCheckingTransactionServices tasksCheckingTransactionServices;
    @Autowired
    private ExamService examService;
    /**
     * Method responsible for get results from AI for single task
     */
    @PostMapping("/task-express")
    public ResponseEntity<String> taskExpress(@RequestBody CustomerTaskDTO customerTaskDTO,
                                                  @RequestHeader("Authorization") String token) throws Exception {
        jwtUtil.extractSubject(token);
        //parse task content for using info when checking and dynamic prompts
        customerTaskDTO.getTask().parseTaskContent();
        //for checking task
        TaskChecker taskChecker = taskCheckerFactory
                .getService(TaskTypeConverter.serviceNameFromTaskType(customerTaskDTO.getTask().getTaskType()));
        //pick needed service for transcription
        APITranscribe apiTranscribe =
                transcribeFactory.getService(customerTaskDTO.getTranscriptionServiceName());
        //get text by audio file from picked service,
        String transcribeAnswer = apiTranscribe
                .transcribe(customerTaskDTO.getAudioPath());
        customerTaskDTO.setTranscribateText(transcribeAnswer);
        //update answer in database and return transcribe text
        customerTaskService.updateAnswerInCustomerTask(customerTaskDTO.getId(), transcribeAnswer);
        customerTaskService.updateCheckStatus(
                customerTaskDTO.getId(),
                CheckStatusEnum.CHECKING.getStatus(),
                TaskResultTypeEnum.EXPRESS.getTaskResultType());

        taskChecker.checkTask(customerTaskDTO);

        return ResponseEntity.ok(CheckStatusEnum.CHECKING.getStatus());
    }
    @PostMapping("/task-express-queue")
    public ResponseEntity<String> taskExpressQueue(@RequestBody CustomerTaskDTO customerTaskDTO,
                                                   @RequestHeader("Authorization") String token){

        if (!tasksCheckingTransactionServices.payForChecking(
                Long.valueOf(jwtUtil.extractSubject(token)),
                taskTypeService.getPriceByTaskType(customerTaskDTO.getTask().getTaskType())))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient funds");
        //check if there is enought balance to user for current task type
        customerTaskService.updateTempCheckingData(customerTaskDTO.getId(), new Gson().toJson(customerTaskDTO));
        customerTaskService.updateCheckStatus(
                customerTaskDTO.getId(),
                CheckStatusEnum.UNTRANSCRIBED.getStatus(),
                TaskResultTypeEnum.EXPRESS.getTaskResultType());

        //Change customerTask status to untranscribate
        //Serialize all data to tempChekingData for next checking and update customerTask
        //return response to user
        return ResponseEntity.ok(CheckStatusEnum.UNTRANSCRIBED.getStatus());
    }
    @PostMapping("/exam-tasks-express-queue")
    public ResponseEntity<String> examTaskExpressQueue(@RequestParam Long examId,
                                                       @RequestHeader("Authorization") String token){
        jwtUtil.extractSubject(token);
        if (!tasksCheckingTransactionServices.payForChecking(
                Long.valueOf(jwtUtil.extractSubject(token)),
                taskTypeService.getTotalPrice()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient funds");
        examService.updateExamCheckStatus(
                examId,
                CheckStatusEnum.CHECKING.getStatus(),
                TaskResultTypeEnum.EXPRESS.getTaskResultType());
        prepareExamCustomerTaskService.prepareCustomerTasks(examId);
        //get 4 tasks by exam Id
        return ResponseEntity.ok("Exam set to checking");
    }
}
