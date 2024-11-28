package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.api.transcribe.APITranscribe;
import com.englishaoe.lesson.api.transcribe.TranscribeFactory;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.utility.AudioFileUtil;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/result")
public class ResultController {
    @Autowired
    private TranscribeFactory transcribeFactory;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomerTaskService customerTaskService;
    @Autowired
    private AudioFileUtil audioFileUtil;
    /**
     * Method responsible for get results from AI for single task
     */
    @PostMapping("/task-express")
    public ResponseEntity<String> taskExpress(@RequestBody CustomerTaskDTO customerTaskDTO,
                                              @RequestHeader("Authorization") String token){
        jwtUtil.extractSubject(token);
        //pick needed service for transcription
        APITranscribe apiTranscribe =
                transcribeFactory.getTranscribeService(customerTaskDTO.getTranscriptionServiceName());
        //get text by audio file from picked service, TODO need full audio path and check if file even available
        String transcribeAnswer = apiTranscribe
                .transcribe(customerTaskDTO.getAudioPath());
        //update answer in database and return transcribe text
        customerTaskService.updateAnswerInCustomerTask(customerTaskDTO.getId(), transcribeAnswer);
        return ResponseEntity.ok(transcribeAnswer);
    }
}
