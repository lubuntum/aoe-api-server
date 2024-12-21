package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.variants.Task;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.dto.lesson.TaskDTO;
import com.englishaoe.lesson.utility.JwtUtil;
import com.englishaoe.lesson.utility.file.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    VariantService variantService;
    @Value("${image.folderDir}")
    private String imageFolderPath;

    @PostMapping("/upload-variant")
    public ResponseEntity<Variant> uploadVariant(@RequestHeader("Authorization") String token,
                                                 @RequestParam("variantImg") MultipartFile variantImg,
                                                 @RequestParam("variantName") String variantName,
                                                 @RequestParam("creationDate") String creationDate){
        jwtUtil.extractSubject(token);//TODO check for admin in future
        return ResponseEntity.ok(variantService.saveVariant(
                variantService.assembleVariant(
                    FileUtil.saveFileToDir(variantImg, imageFolderPath),
                    variantName,
                    creationDate)));
    }
    @DeleteMapping("/delete-variant/{variantId}")
    public ResponseEntity<String> deleteVariant(@RequestHeader("Authorization") String token,
                                                @PathVariable("variantId") Long variantId){
        variantService.deleteVariantById(variantId);
        return ResponseEntity.status(HttpStatus.OK).body("Variant deleted");
    }
    @PostMapping("/upload-tasks")
    public ResponseEntity<String> uploadTasks(@RequestHeader("Authorization") String token,
                                              @RequestParam("variantId") Long variantId,
                                              @RequestParam("tasks") String tasksJSON,
                                              @RequestParam("img") MultipartFile secondTaskImage,
                                              @RequestParam("secondImg") MultipartFile fourthTaskImageFirst,
                                              @RequestParam("firstImg") MultipartFile fourthTaskImageSecond) {
        jwtUtil.extractSubject(token);//TODO check for admin in future
        tasksJSON = String.format(tasksJSON,
                FileUtil.saveFileToDir(secondTaskImage, imageFolderPath),
                FileUtil.saveFileToDir(fourthTaskImageFirst, imageFolderPath),
                FileUtil.saveFileToDir(fourthTaskImageSecond, imageFolderPath));
        variantService.saveTasksByVariant(variantService.assembleTasks(tasksJSON), variantId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tasks created");
    }
}
