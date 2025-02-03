package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.config.AppConfig;
import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.repository.CustomerRepository;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.PartnerService;
import com.englishaoe.lesson.database.services.PartnerTypeService;
import com.englishaoe.lesson.dto.account.CustomerRegistrationDTO;
import com.englishaoe.lesson.dto.account.CustomerRegistrationDTOMapper;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import com.englishaoe.lesson.dto.authorization.LoginResponseDTO;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.JwtUtil;
import com.englishaoe.lesson.utility.PassValidationUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PassValidationUtil passValidationUtil;
    @Autowired
    private CustomerServices customerServices;
    @Autowired
    private PartnerService partnerService;
    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody CustomerRegistrationDTO customer){
        if (customer == null)
            throw new RegularException("No customer data provided", HttpStatus.BAD_REQUEST.value());
        if (customerServices.emailExists(customer.getEmail()))
            throw new RegularException("Email already exists", HttpStatus.CONFLICT.value());
        customer.setPassword(passValidationUtil.hashPassword(customer.getPassword()));
        customer.setCustomerId(customerServices.saveCustomer(CustomerRegistrationDTOMapper.parse(customer)).getId());
        if(customer.getIsPartnerProposal()) partnerService.createNotApprovedPartnerForCustomerAccount(customer);
        if (customer.getCustomerId() == null)
            throw new RegularException("Unexpected error occurred while saving customer", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration succeed");
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody CustomerAuthDTO customerAuthDTO){
        CustomerAuthDTO customerCredential = customerServices.getCustomerCredentialByEmail(customerAuthDTO.getEmail().toLowerCase());
        if (customerCredential == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!passValidationUtil.validatePassword(customerAuthDTO.getPassword(), customerCredential.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(new LoginResponseDTO(jwtUtil.generateToken(String.valueOf(customerCredential.getId()))));
    }
    @GetMapping("/validate")
    public String validateTokenTest(@RequestHeader("Authorization") String token){
        System.out.print("Start validation..");
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtUtil.env.getProperty(AppConfig.SECRET_KEY_PROPERTY).getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
            String name = Jwts.parser().setSigningKey(jwtUtil.env.getProperty(AppConfig.SECRET_KEY_PROPERTY).getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody().getSubject();
            String subject = claims.getSubject();
            return claims.toString();
        } catch (Exception e){
            throw new RuntimeException("Invalid token" + e.getMessage());
        }
    }
}
