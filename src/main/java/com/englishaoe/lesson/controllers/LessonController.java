package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import com.englishaoe.lesson.dto.lesson.VariantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {
    @Autowired
    VariantService variantService;
    @GetMapping("/variants")
    public ResponseEntity<List<VariantDTO>> variantsData(){
        return ResponseEntity.ok(variantService.getAllVariantsDTO());
    }
}
