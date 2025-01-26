package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
