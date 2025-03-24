package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.config.AppConfig;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.PartnerService;
import com.englishaoe.lesson.dto.account.CustomerRegistrationDTO;
import com.englishaoe.lesson.dto.account.CustomerRegistrationDTOMapper;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import com.englishaoe.lesson.dto.authorization.LoginResponseDTO;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.services.email.EmailService;
import com.englishaoe.lesson.utility.JwtUtil;
import com.englishaoe.lesson.utility.ParseUtil;
import com.englishaoe.lesson.utility.PassValidationUtil;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomerServices customerServices;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private EmailService emailService;
    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody CustomerRegistrationDTO customer) throws IOException, MessagingException {
        if (customer == null)
            throw new RegularException("No customer data provided", HttpStatus.BAD_REQUEST.value());
        if (customerServices.emailExists(customer.getEmail()))
            throw new RegularException("Email already exists", HttpStatus.CONFLICT.value());
        customer.setPassword(PassValidationUtil.hashPassword(customer.getPassword()));
        customer.setCustomerId(customerServices.saveCustomer(CustomerRegistrationDTOMapper.parse(customer)).getId());
        if(customer.getIsPartnerProposal()) partnerService.createNotApprovedPartnerForCustomerAccount(customer);
        if (customer.getCustomerId() == null)
            throw new RegularException("Unexpected error occurred while saving customer", HttpStatus.INTERNAL_SERVER_ERROR.value());
        emailService.sendPageMessage(
                customer.getEmail(),
                "Please Confirm Your Email Address",
                emailService.assemblyEmailRegistrationText(customer.getName(), jwtUtil.generateToken(String.valueOf(customer.getCustomerId()))));
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration succeed");
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody CustomerAuthDTO customerAuthDTO){
        CustomerAuthDTO customerCredential = customerServices.getCustomerCredentialByEmail(customerAuthDTO.getEmail().toLowerCase());
        if (customerCredential == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!PassValidationUtil.validatePassword(customerAuthDTO.getPassword(), customerCredential.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!customerServices.isCustomerEmailConfirmed(customerCredential.getEmail()))
            throw new RegularException("Please confirm your email", HttpStatus.FORBIDDEN.value());
        customerServices.updateLastLogin(LocalDateTime.now(), customerCredential.getId());
        return ResponseEntity.ok(new LoginResponseDTO(jwtUtil.generateToken(String.valueOf(customerCredential.getId()))));
    }
    @PostMapping("/reset-password-auth")
    public ResponseEntity<Boolean> resetPasswordAuth(@RequestHeader("Authorization") String token,
                                                    @RequestBody Map<String, String> resetData) {
        if (!customerServices.validateCustomerPassword(Long.valueOf(jwtUtil.extractSubject(token)), resetData.get("originalPassword")))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        customerServices.resetPassword(Long.valueOf(jwtUtil.extractSubject(token)), resetData.get("password"));
        return ResponseEntity.ok().body(true);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestHeader("Authorization") String token,
                                                @RequestBody Map<String, String> resetData) {
        try {
            if (resetData.get("password") == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No content provided");
            CustomerAuthDTO tokenInfo = ParseUtil.deserialize(jwtUtil.extractSubject(token), new TypeToken<CustomerAuthDTO>(){});
            customerServices.resetPassword(tokenInfo.getId(), resetData.get("password"));
            return ResponseEntity.ok().body("Password reset");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
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
