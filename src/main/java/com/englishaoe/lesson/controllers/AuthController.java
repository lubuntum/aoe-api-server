package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.config.AppConfig;
import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.repository.CustomerRepository;
import com.englishaoe.lesson.database.services.CustomerServices;
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

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer){
        //Some repo for save user
        return ResponseEntity.ok("Registration is succeed");
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody CustomerAuthDTO customerAuthDTO){
        CustomerAuthDTO customerCredential = customerServices.getCustomerCredentialByUsername(customerAuthDTO.getUsername());
        if (passValidationUtil.validatePassword(customerAuthDTO.getPassword(), customerCredential.getPassword()))
            return ResponseEntity.ok(new LoginResponseDTO(jwtUtil.generateToken(String.valueOf(customerCredential.getId()))));
        /*Remove condition and add search and result from database, now test id = 123*/
        throw new RegularException("Invalid user credential", HttpStatus.UNAUTHORIZED.value());
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
