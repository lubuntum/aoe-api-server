package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.results.Exam;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.services.ExamService;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.dto.lesson.TaskDTO;
import com.englishaoe.lesson.dto.lesson.VariantThemeDTO;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    JwtUtil jwtUtil;
    @GetMapping("/variants")
    public ResponseEntity<List<VariantThemeDTO>> variantsData(){
        return ResponseEntity.ok(variantService.getAllVariantsDTO());
    }

    @GetMapping("/variant/{id}/tasks")
    public ResponseEntity<List<TaskDTO>> getVariantById(@PathVariable("id") Long id){
        List<TaskDTO> taskList = variantService.getTasksByVariantId(id);
        return ResponseEntity.ok(taskList);
    }
    @PostMapping("/exam")
    public ResponseEntity<String> createExam(@RequestBody Exam exam,
                                             @RequestHeader("Authorization") String token){
        Long customerId = Long.valueOf(jwtUtil.extractSubject(token));

    return ResponseEntity.ok("Exam created successfully");
    }
}
