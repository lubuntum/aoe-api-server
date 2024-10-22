package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import com.englishaoe.lesson.dto.lesson.VariantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//TODO try to get Variant with List<Task> where each Task has TaskType field
// 1. Can use DTO like VariantExam
// 2. Can get Variant with Task and then find TaskType for each Task (transaction)
@RestController
@RequestMapping("/api/lesson")
public class LessonController {
    @Autowired
    VariantService variantService;
    @GetMapping("/variants")
    public ResponseEntity<List<VariantDTO>> variantsData(){
        return ResponseEntity.ok(variantService.getAllVariantsDTO());
    }

    @GetMapping("/variant/{id}")
    public ResponseEntity<Variant> getVariantById(@PathVariable("id") Long id){
        Variant variant = variantService.getVariantById(id);
        return ResponseEntity.ok(variant);
    }
}
