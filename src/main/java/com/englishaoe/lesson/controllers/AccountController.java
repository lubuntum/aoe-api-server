package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.results.Exam;
import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.database.repository.CustomerRepository;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.ExamService;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.dto.account.CustomerAccountDTO;
import com.englishaoe.lesson.dto.account.AccountMapper;
import com.englishaoe.lesson.dto.account.CustomerHeaderDTO;
import com.englishaoe.lesson.exceptions.jwtkeys.JwtExpiredException;
import com.englishaoe.lesson.utility.JwtUtil;
import com.englishaoe.lesson.utility.filter.ExamFilter;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    JwtUtil jwtUtil;
    //repository services
    @Autowired
    VariantService variantService;
    @Autowired
    CustomerServices customerServices;
    @Autowired
    CustomerTaskService customerTaskService;
    @Autowired
    ExamService examService;
    //TODO add some method or filter for some routes which requier authentification like /customer, /header
    @Autowired
    ExamFilter examFilter;
    @GetMapping("/customer")
    public ResponseEntity<CustomerAccountDTO> customerAccountData(@RequestHeader("Authorization") String token){
        CustomerAccountDTO customerAccountDTO = customerServices.getCustomerAccountDataById(Long.valueOf(jwtUtil.extractSubject(token)));
        //return DTO
        return ResponseEntity.ok(customerAccountDTO);
    }
    @GetMapping("/header")
    public ResponseEntity<CustomerHeaderDTO> headerData(@RequestHeader("Authorization") String token) {
        try {
            CustomerHeaderDTO customerHeaderDTO = customerServices.getCustomerHeaderDataById(Long.valueOf(jwtUtil.extractSubject(token)));
            return ResponseEntity.ok(customerHeaderDTO);
        }
        catch (ExpiredJwtException e) {
            throw new JwtExpiredException("JWT token is expired", e);
        }
    }
    @GetMapping("/customer-by-id")
    public ResponseEntity<CustomerAccountDTO> customerTest(@RequestHeader("Authorization") String token ){
        CustomerAccountDTO customerAccountDTO = customerServices.getCustomerAccountDataById(Long.valueOf(jwtUtil.extractSubject(token)));
        return ResponseEntity.ok(customerAccountDTO);
    }
    /** Just get all variants which customer completed (for result panel)*/
    @GetMapping("/customer/completed-variants")
    public ResponseEntity<List<Variant>> getCustomerCompletedVariants(@RequestHeader("Authorization") String token) {
        List<Exam> exams = examService.getExamsByCustomerId(Long.valueOf(jwtUtil.extractSubject(token)));
        List<Long> variantsIds =  examFilter.filterExamsByUniqueVariantId(exams);
        List<Variant> variants = variantService.getVariantsByIds(variantsIds);

        return ResponseEntity.ok(variants);
    }
    /**Get all exams for customer completed by some specific variant*/
    @GetMapping("/customer/exams-by-variant")
    public ResponseEntity<List<Exam>> getExamsCompletedByVariant(@RequestHeader("Authorization") String token,
                                                                 @RequestParam("variantId") Long variantId){
        List<Exam> exams = examService.getExamsByCustomerIdAndVariantId(
                Long.valueOf(jwtUtil.extractSubject(token)), variantId);
        return ResponseEntity.ok(exams);
    }
    /** get all customerTask completed for the task out of exam (just one Task)*/
    @GetMapping("/customer/task-by-variant")
    public ResponseEntity<List<CustomerTask>> getTaskResultCompletedByTask(@RequestHeader("Authorization") String token,
                                                                           @RequestParam("taskId") Long taskId){
        List<CustomerTask> customerTasks = customerTaskService.getCustomerTaskOutOfExam(taskId, Long.valueOf(jwtUtil.extractSubject(token)));
        return ResponseEntity.ok(customerTasks);
    }
}
