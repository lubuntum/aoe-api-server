package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.config.AppConfig;
import com.englishaoe.lesson.database.entity.Student;
import com.englishaoe.lesson.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Student testStudent;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Student student){
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        testStudent = student;//TEST!
        //Some repo for save user
        System.out.print("TEST");
        return ResponseEntity.ok("Registration is succeed");
    }
    @PostMapping("/login")
    public String login(@RequestBody Student student){
        if(student.getUsername().equals("Pavel")
                && passwordEncoder.matches(student.getPassword(), passwordEncoder.encode("123456"))){
            System.out.print("Try to return");
            return jwtUtil.generateToken(student.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
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
