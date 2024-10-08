package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.config.AppConfig;
import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.dto.LoginResponse;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.JwtUtil;
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

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer){
        //Some repo for save user
        return ResponseEntity.ok("Registration is succeed");
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Customer customer){
        /*Remove condition and add search and result from database*/
        if(customer.getUsername().equals("Pavel")
                && passwordEncoder.matches(customer.getPassword(), passwordEncoder.encode("123456"))){//?? ????
            LoginResponse loginResponse = new LoginResponse(jwtUtil.generateToken(String.valueOf(customer.getId())));
            return ResponseEntity.ok(loginResponse);
        }
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
