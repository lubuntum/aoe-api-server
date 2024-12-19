package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    VariantService variantService;

    @PostMapping("/upload-variant")
    public ResponseEntity<Variant> uploadVariant(@RequestHeader("Authorization") String token,
                                                 @RequestParam("variantImg") String variantImg,
                                                 @RequestParam("variantName") String variantName,
                                                 @RequestParam("creationDate") String creationDate){

        jwtUtil.extractSubject(token);
        //TODO save file, save ready variant to db, send variant back
        return null;
    }
}
