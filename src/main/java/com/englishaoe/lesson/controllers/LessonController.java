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
import com.englishaoe.lesson.dto.lesson.variant.VariantDTO;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.JwtUtil;
import com.englishaoe.lesson.utility.file.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

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
    @Value("${audio.folderDir}")
    private String audioFolderPath;
    /** All available variants*/
    @GetMapping("/variants")
    public ResponseEntity<List<VariantDTO>> getVisibleVariants() throws SQLException {
        return ResponseEntity.ok(variantService.getVisibleVariantsDTO());
    }
    /** Get variant's tasks by id, better call endpoint like getTasksByVariantId*/
    @GetMapping("/variant/{id}/tasks")
    public ResponseEntity<List<TaskDTO>> getVariantById(@PathVariable("id") Long id){
        List<TaskDTO> taskList = variantService.getTasksByVariantId(id);
        return ResponseEntity.ok(taskList);
    }
    @GetMapping("/variant/{id}")
    public ResponseEntity<Variant> getVariant(@PathVariable("id") Long id){
        Variant variant = variantService.getVariantById(id);
        return ResponseEntity.ok(variant);
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
        List<CustomerTask> examTasks = customerTaskService.getCustomerTasksByExamId(examId);
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
        //customerTask.setAudioPath(audioFileUtil.saveAudioFile(file));
        customerTask.setAudioPath(FileUtil.saveFileToDir(file, audioFolderPath, false));
        customerTask.setAnswer(null);
        CustomerTask result = customerTaskService.saveCustomerTask(customerTask);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/change-exam-status")
    public ResponseEntity<String> changeExamStatus(@RequestHeader("Authorization") String token,
                                                   @RequestParam("examId")Long examId,
                                                   @RequestParam("statusName") String statusName,
                                                   @RequestParam("checkType") String checkType){
        jwtUtil.extractSubject(token);
        if (customerTaskService.isAllCustomerTasksCheckedForExam(examId))
            examService.updateExamCheckStatus(examId, statusName, checkType);
        return ResponseEntity.ok("Changed to " + statusName);
    }
}
