package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import com.englishaoe.lesson.dto.authorization.LoginResponseDTO;
import com.englishaoe.lesson.dto.account.CustomerAccountDTO;
import com.englishaoe.lesson.dto.account.CustomerHeaderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

//with JPQL
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /*
    @Query("SELECT new com.englishaoe.lesson.dto.account.CustomerHeaderDTO(c.username, c.attemptsAI, c.attemptsExpert)" +
            " FROM Customer c WHERE c.id=:id")
    CustomerHeaderDTO findCustomerHeaderData(@Param("id") Long id);
    */
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.roles WHERE c.id = :id")
    Customer findCustomerWithRoles(@Param("id") Long id);
    @Query("SELECT new com.englishaoe.lesson.dto.account.CustomerAccountDTO(" +
            "c.username, c.email, c.name, c.secondName, c.registrationDate,c.currentBalance , c.purchaseSubDate, c.expireSubDate, " +
            "p.partnerName) FROM Customer c " +
            "LEFT JOIN c.promocodeList pc " +
            "LEFT JOIN pc.partner p " +
            "WHERE c.id=:id")
    CustomerAccountDTO findCustomerAccountData(@Param("id") Long id);


    @Query("SELECT new com.englishaoe.lesson.dto.authorization.CustomerAuthDTO(c.id, c.email, c.password) " +
            "FROM Customer c WHERE c.email = :email")
    CustomerAuthDTO findCustomerCredentialByEmail(@Param("email") String email);
    Customer findByEmail(String email);
    @Query("SELECT c.id from Customer c WHERE c.email = :email")
    Long findCustomerIdByEmail(String email);
    @Query("SELECT c.currentBalance from Customer c WHERE c.id = :customerId")
    BigDecimal findCurrentBalanceById(Long customerId);
}
