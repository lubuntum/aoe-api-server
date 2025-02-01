package com.englishaoe.lesson.services;

import com.englishaoe.lesson.database.entity.role.RoleEnum;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    CustomerServices customerServices;

    public boolean isCustomerAdmin(String token) {
        return customerServices.isCustomerHasAdminRole(Long.valueOf(jwtUtil.extractSubject(token)));
    }
    public boolean isCustomerHasProvidedRole(String token, RoleEnum roleEnum){
        return customerServices.isCustomerHasProvidedRole(Long.valueOf(jwtUtil.extractSubject(token)), roleEnum);
    }
}
