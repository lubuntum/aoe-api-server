package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.Exam;
import com.englishaoe.lesson.database.entity.variants.Task;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.ExamService;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.dto.lesson.ExamDTO;
import com.englishaoe.lesson.dto.lesson.TaskDTO;
import com.englishaoe.lesson.dto.lesson.VariantThemeDTO;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.AudioFileUtil;
import com.englishaoe.lesson.utility.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
//TODO try to get Variant with List<Task> where each Task has TaskType field
// 1. Can use DTO like VariantExam
// 2. Can get Variant with Task and then find TaskType for each Task (transaction)
@RestController
@RequestMapping("/api/lesson")
public class LessonController {
    @Autowired
    VariantService variantService;
    @Autowired
    ExamService examService;
    @Autowired
    CustomerTaskService customerTaskService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AudioFileUtil audioFileUtil;
    /** All available variants*/
    @GetMapping("/variants")
    public ResponseEntity<List<VariantThemeDTO>> variantsData() throws SQLException {
        return ResponseEntity.ok(variantService.getAllVariantsDTO());
    }
    /** Get variant's tasks by id, better call endpoint like getTasksByVariantId*/
    @GetMapping("/variant/{id}/tasks")
    public ResponseEntity<List<TaskDTO>> getVariantById(@PathVariable("id") Long id){
        List<TaskDTO> taskList = variantService.getTasksByVariantId(id);
        return ResponseEntity.ok(taskList);
    }
    /** Get specific task by id*/
    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        Task task = variantService.getTaskById(id);
        return ResponseEntity.ok(task);
    }
    /** Get exam result (List of customerTask - 4)*/
    @GetMapping("/result")
    public ResponseEntity<List<CustomerTask>> getExamResults(@RequestParam("examId") Long examId){
        List<CustomerTask> examTasks = customerTaskService.getCustomerTaskByExamId(examId);
        return ResponseEntity.ok(examTasks);
    }
    /** Get customerTask*/
    @GetMapping("/customer-task")
    public ResponseEntity<CustomerTask> getCustomerTaskById(@RequestParam("customerTaskId") Long customerTaskId){
        CustomerTask customerTask = customerTaskService.getCustomerTaskById(customerTaskId);
        return ResponseEntity.ok(customerTask);
    }
    /** Create exam and return exam id*/
    @PostMapping("/exam")
    public ResponseEntity<ExamDTO> createExam(@RequestBody Exam exam,
                                              @RequestHeader("Authorization") String token){
        exam.setCustomerId(Long.valueOf(jwtUtil.extractSubject(token)));
        examService.createExam(exam);

    return ResponseEntity.ok(new ExamDTO(exam.getId(), exam.getExamCompleteDate()));
    }
    /** Save all data for task */
    @PostMapping("/user-task")
    public ResponseEntity<CustomerTask> saveTaskResult(@RequestParam("file") MultipartFile file,
                                                 @RequestPart("customerTask") CustomerTask customerTask,
                                                 @RequestHeader("Authorization") String token) throws JsonProcessingException, SQLException {
        if (file.isEmpty()) throw new RegularException("file is empty", HttpStatus.BAD_REQUEST.value());
        customerTask.setCustomerId(Long.valueOf(jwtUtil.extractSubject(token)));
        customerTask.setAudioPath(audioFileUtil.saveAudioFile(file));
        customerTask.setAnswer("{}");
        CustomerTask result = customerTaskService.saveCustomerTask(customerTask);
        return ResponseEntity.ok(result);
    }
}
