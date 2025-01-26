package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.role.Role;
import com.englishaoe.lesson.database.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByName(String name){
        return roleRepository.findRoleByName(name);
    }
}
