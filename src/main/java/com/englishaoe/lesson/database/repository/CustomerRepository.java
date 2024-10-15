package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import com.englishaoe.lesson.dto.authorization.LoginResponseDTO;
import com.englishaoe.lesson.dto.account.CustomerAccountDTO;
import com.englishaoe.lesson.dto.account.CustomerHeaderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//with JPQL
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT new com.englishaoe.lesson.dto.account.CustomerHeaderDTO(c.username, c.attemptsAI, c.attemptsExpert)" +
            " FROM Customer c WHERE c.id=:id")
    CustomerHeaderDTO findCustomerHeaderData(@Param("id") Long id);
    @Query("SELECT new com.englishaoe.lesson.dto.account.CustomerAccountDTO(" +
            "c.username, c.email, c.name, c.secondName, c.registrationDate, c.attemptsAI, c.attemptsExpert, c.actualSubscriptionDate," +
            "p.partnerName) FROM Customer c " +
            "JOIN c.promocodeList pc " +
            "JOIN pc.partner p " +
            "WHERE c.id=:id")
    CustomerAccountDTO findCustomerAccountData(@Param("id") Long id);

    @Query("SELECT new com.englishaoe.lesson.dto.authorization.CustomerAuthDTO(c.id, c.username, c.password) " +
            "FROM Customer c WHERE c.username = :username")
    CustomerAuthDTO findCustomerCredentialDataByUsername(@Param("username") String username);
}
