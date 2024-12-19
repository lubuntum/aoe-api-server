package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.api.transcribe.APITranscribe;
import com.englishaoe.lesson.api.transcribe.TranscribeFactory;
import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.TaskResultService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.taskcheck.TaskCheckerFactory;
import com.englishaoe.lesson.taskcheck.checkers.TaskTypeConverter;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/result")
public class ResultController {
    // factories for pick services by name in request
    @Autowired
    private TranscribeFactory transcribeFactory;
    @Autowired
    private TaskCheckerFactory taskCheckerFactory;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomerTaskService customerTaskService;
    @Autowired
    private TaskResultService taskResultService;
    /**
     * Method responsible for get results from AI for single task
     */
    @PostMapping("/task-express")
    public ResponseEntity<String> taskExpress(@RequestBody CustomerTaskDTO customerTaskDTO,
                                                  @RequestHeader("Authorization") String token) throws IllegalAccessException {
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
}
