package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.dto.lesson.variant.VariantDTO;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.JwtUtil;
import com.englishaoe.lesson.utility.file.FileUtil;
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
    @Autowired
    CustomerServices customerServices;
    @Value("${image.folderDir}")
    private String imageFolderPath;
    @GetMapping("/validate")
    public boolean validateAdmin(@RequestHeader("Authorization") String token) {
        return customerServices.isCustomerHasAdminRole(Long.valueOf(jwtUtil.extractSubject(token)));
    }
    @PostMapping("/upload-variant")
    public ResponseEntity<Variant> uploadVariant(@RequestHeader("Authorization") String token,
                                                 @RequestParam("variantImg") MultipartFile variantImg,
                                                 @RequestParam("variantName") String variantName,
                                                 @RequestParam("creationDate") String creationDate){

        //jwtUtil.extractSubject(token);//TODO check for admin in future
        if (!customerServices.isCustomerHasAdminRole(Long.valueOf(jwtUtil.extractSubject(token))))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());

        return ResponseEntity.ok(variantService.saveVariant(
                variantService.assembleVariant(
                    FileUtil.saveFileToDir(variantImg, imageFolderPath, true),
                    variantName,
                    creationDate)));
    }
    @PostMapping("/variant-visibility/{variantId}")
    public ResponseEntity<VariantDTO> hideVariant(@RequestHeader("Authorization") String token,
                                              @PathVariable("variantId")Long variantId,
                                              @RequestParam("visibility") Boolean visibility){
        if (!customerServices.isCustomerHasAdminRole(Long.valueOf(jwtUtil.extractSubject(token))))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(variantService.updateVariantVisibility(variantId, visibility));
    }
    @GetMapping("/variants")
    public ResponseEntity<List<VariantDTO>> getAllVariants(@RequestHeader("Authorization") String token){
        if (!customerServices.isCustomerHasAdminRole(Long.valueOf(jwtUtil.extractSubject(token))))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(variantService.getAllVariantsDTO());
    }
    @DeleteMapping("/delete-variant/{variantId}")
    public ResponseEntity<String> deleteVariant(@RequestHeader("Authorization") String token,
                                                @PathVariable("variantId") Long variantId){
        if (!customerServices.isCustomerHasAdminRole(Long.valueOf(jwtUtil.extractSubject(token))))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
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
        if (!customerServices.isCustomerHasAdminRole(Long.valueOf(jwtUtil.extractSubject(token))))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        tasksJSON = String.format(tasksJSON,
                FileUtil.saveFileToDir(secondTaskImage, imageFolderPath, true),
                FileUtil.saveFileToDir(fourthTaskImageFirst, imageFolderPath, true),
                FileUtil.saveFileToDir(fourthTaskImageSecond, imageFolderPath, true));
        variantService.saveTasksByVariant(variantService.assembleTasks(tasksJSON), variantId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tasks created");
    }
}
