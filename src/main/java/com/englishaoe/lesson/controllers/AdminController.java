package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.variants.TaskType;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.dto.lesson.variant.VariantDTO;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.services.AuthorizationService;
import com.englishaoe.lesson.services.file.VariantTasksFilesHandlerService;
import com.englishaoe.lesson.utility.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    VariantService variantService;
    @Autowired
    TaskTypeService taskTypeService;
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    CustomerServices customerServices;
    @Autowired
    VariantTasksFilesHandlerService variantTasksFilesHandlerService;
    @Value("${image.folderDir}")
    private String imageFolderPath;
    @Value("${speaker.folderDir}")
    private String speakerFolderPath;
    @GetMapping("/validate")
    public boolean validateAdmin(@RequestHeader("Authorization") String token) {
        return authorizationService.isCustomerAdmin(token);
    }
    @GetMapping("/tasks-types")
    public ResponseEntity<List<TaskType>> getAllTaskType(@RequestHeader("Authorization") String token) {
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(taskTypeService.getAllTaskType());
    }
    @GetMapping("/customer-id-by-email")
    public ResponseEntity<Long> getCustomerIdByEmail(@RequestHeader("Authorization") String token,
                                                     @RequestParam("email") String email){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        Long customerId = customerServices.getCustomerIdByEmail(email);
        if (customerId == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customerId);
    }
    @PostMapping("/add-balance-to-customer")
    public ResponseEntity<Boolean> addBalanceToCustomer(@RequestHeader("Authorization") String token,
                                                        @RequestParam("customerId") Long customerId,
                                                        @RequestParam("amount") BigDecimal amount){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(customerServices.addBalanceToCustomerById(customerId, amount));
    }
    @PostMapping("/update-prompt")
    public ResponseEntity<TaskType> updatePrompt(@RequestHeader("Authorization") String token,
                                                @RequestParam("taskTypeId") Long taskTypeId,
                                                @RequestParam("prompt") String prompt){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(taskTypeService.updateTaskType(taskTypeId, prompt));
    }
    /**
     * Save variant and return variant with id
     * which later can be used for tasks saving
     * */
    @PostMapping("/upload-variant")
    public ResponseEntity<Variant> uploadVariant(@RequestHeader("Authorization") String token,
                                                 @RequestParam("variantImg") MultipartFile variantImg,
                                                 @RequestParam("variantTheme") String variantTheme,
                                                 @RequestParam("creationDate") String creationDate){

        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());

        return ResponseEntity.ok(variantService.saveVariant(
                variantService.assembleVariant(
                    FileUtil.saveFileToDir(variantImg, imageFolderPath, true),
                    variantTheme,
                    creationDate)));
    }
    /**
     * Change variant visibility for users
     * return updated variant
     * */
    @PostMapping("/variant-visibility/{variantId}")
    public ResponseEntity<VariantDTO> hideVariant(@RequestHeader("Authorization") String token,
                                              @PathVariable("variantId")Long variantId,
                                              @RequestParam("visibility") Boolean visibility){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(variantService.updateVariantVisibility(variantId, visibility));
    }
    @GetMapping("/variants")
    public ResponseEntity<List<VariantDTO>> getAllVariants(@RequestHeader("Authorization") String token){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(variantService.getAllVariantsDTO());
    }
    @PostMapping("/edit-variant/{variantId}")
    public ResponseEntity<String> editVariant(@RequestHeader("Authorization") String token,
                                              @PathVariable("variantId") Long variantId,
                                              @RequestParam(value = "variantImg", required = false) MultipartFile variantImg,
                                              @RequestParam(value = "variantTheme", required = false) String variantTheme){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        Variant variant = variantService.getVariantById(variantId);
        if (variant == null) return ResponseEntity.status(HttpStatus.FOUND).build();
        if (variantImg != null) {
            FileUtil.deleteFileFromDir(
                    variant.getImagePath().replace("images",""),
                    imageFolderPath);
            variant.setImagePath(FileUtil.saveFileToDir(variantImg, imageFolderPath, true));
        }
        if (variantTheme != null)
            variant.setTheme(variantTheme);
        variantService.saveVariant(variant);
        //check if image not null then remove old one, and save new one, update path in variant
        //if variantName not null, then just find variant and update name, save it
        return ResponseEntity.ok("Variant updated");
    }
    @PostMapping("/edit-variant-tasks/{variantId}")
    public ResponseEntity<String> editVariantTasks(@RequestHeader("Authorization") String token,
                                              @PathVariable("variantId") Long variantId,
                                              @RequestParam(value = "tasks") String tasksJson,
                                              @RequestParam(value = "img", required = false) MultipartFile secondTaskImage,
                                              @RequestParam(value = "secondImg", required = false) MultipartFile fourthTaskImageFirst,
                                              @RequestParam(value = "firstImg", required = false) MultipartFile fourthTaskImageSecond,
                                              @RequestParam(value = "speakerRecord", required = false) MultipartFile speakerRecord,
                                              @RequestParam(value = "questionsRecords", required = false) List<MultipartFile> questionsRecords){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        tasksJson = variantTasksFilesHandlerService.updateAllFilesForVariantTasks(variantService.getVariantById(variantId),tasksJson,
                secondTaskImage, fourthTaskImageFirst, fourthTaskImageSecond, speakerRecord, questionsRecords);
        //TODO do the same and then update taskJson in variant with assemble function , remove old task, save new one
        //variantService.removeTasksByVariantId(variantId);
        variantService.updateTasksForVariant(variantService.assembleTasks(tasksJson), variantService.getVariantById(variantId));

        return ResponseEntity.ok("Variant updated");
    }
    @DeleteMapping("/delete-variant/{variantId}")
    public ResponseEntity<String> deleteVariant(@RequestHeader("Authorization") String token,
                                                @PathVariable("variantId") Long variantId){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        try{
            //delete all associated files
            Variant variant = variantService.getVariantById(variantId);
            variantTasksFilesHandlerService.deleteAllFilesFromVariantTasks(variant);
            variantService.deleteVariantById(variantId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Variant deleted");
    }
    /**
     * Save all tasks with images by variant id
     * */
    @PostMapping("/upload-tasks")//better name uploadVariantData, endpoint upload-variant
    public ResponseEntity<String> uploadTasks(@RequestHeader("Authorization") String token,
                                              @RequestParam("variantId") Long variantId,
                                              @RequestParam("tasks") String tasksJson,
                                              @RequestParam("img") MultipartFile secondTaskImage,
                                              @RequestParam("secondImg") MultipartFile fourthTaskImageFirst,
                                              @RequestParam("firstImg") MultipartFile fourthTaskImageSecond,
                                              @RequestParam("speakerRecord") MultipartFile speakerRecord,
                                              @RequestParam("questionsRecords") List<MultipartFile> questionsRecords) {
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        tasksJson = variantTasksFilesHandlerService
                .saveImagesForVariantTask(tasksJson, secondTaskImage, fourthTaskImageFirst, fourthTaskImageSecond);
        tasksJson = variantTasksFilesHandlerService
                .saveSpeakerRecordsForVariantTask(tasksJson, speakerRecord, questionsRecords);
        variantService.createTasksForVariant(variantService.assembleTasks(tasksJson), variantId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tasks created");
    }

}
