package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.entity.role.Role;
import com.englishaoe.lesson.database.repository.CustomerRepository;
import com.englishaoe.lesson.dto.account.CustomerAccountDTO;
import com.englishaoe.lesson.dto.account.CustomerHeaderDTO;
import com.englishaoe.lesson.dto.authorization.CustomerAuthDTO;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServices {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleService roleService;
    public Customer getCustomerById(Long id){
        return customerRepository.findById(id).orElseThrow(
                ()->new RegularException("User not found", HttpStatus.FORBIDDEN.value()));
    }
    public CustomerHeaderDTO getCustomerHeaderDataById(Long id) {
        Customer customer = customerRepository.findCustomerWithRoles(id);
        if (customer == null) return null;
        List<String> roleNames = customer
                .getRoles()
                .stream()
                .map(Role::getName)
                .toList();
        return new CustomerHeaderDTO(customer.getUsername(), customer.getCurrentBalance(), roleNames);
    }
    public boolean isCustomerHasAdminRole(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) return false;
        if (customer.getRoles() == null) return false;
        return customer.getRoles().stream().anyMatch(role -> "admin".equals(role.getName()));
    }
    public CustomerAccountDTO getCustomerAccountDataById(Long id){
        return customerRepository.findCustomerAccountData(id);
    }
    public CustomerAuthDTO getCustomerCredentialByEmail(String email){
        return customerRepository.findCustomerCredentialByEmail(email);
    }
    public Boolean addBalanceToCustomerById(Long id, BigDecimal amount){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) return false;
        Customer customer = customerOptional.get();
        BigDecimal currentBalance =
                Optional.ofNullable(customer.getCurrentBalance()).orElse(BigDecimal.ZERO);
        customer.setCurrentBalance(currentBalance.add(amount));
        customerRepository.save(customer);
        return true;
    }
    public Boolean subBalanceToCustomerById(Long id, BigDecimal price) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) return false;
        Customer customer = customerOptional.get();
        BigDecimal currentBalance =
                Optional.ofNullable(customer.getCurrentBalance()).orElse(BigDecimal.ZERO);
        if (currentBalance.subtract(price).compareTo(BigDecimal.ZERO) < 0)
            return false;
        customer.setCurrentBalance(currentBalance.subtract(price));
        customerRepository.save(customer);
        return true;
    }
    //TODO check if previous customer sub not expired, then add remains days to current exp date
    public void updateCustomerSubscriptionInfo(String purchaseDate, String expireDate, Long customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new NullPointerException("Customer not found"));
        customer.setPurchaseSubDate(purchaseDate);
        customer.setExpireSubDate(DateUtil.extendSubscriptionPeriod(expireDate, customer.getExpireSubDate()));
        customerRepository.save(customer);
    }
    public Customer saveCustomer(Customer customer) {
        customer.setEmail(customer.getEmail().toLowerCase());
        return customerRepository.save(customer);
    }
    public void addRoleToCustomer(Long customerId, String roleName) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()-> new RegularException("Customer not found", HttpStatus.FORBIDDEN.value()));
        Role role = roleService.getRoleByName(roleName);
        if (role == null) throw new RegularException("Role not found", HttpStatus.FORBIDDEN.value());
        if (customer.getRoles().stream().anyMatch(r -> r.getName().equals(role.getName()))) return;
        customer.getRoles().add(role);
        customerRepository.save(customer);
    }
    public Customer getCustomerByEmail(String email){
        return customerRepository.findByEmail(email);
    }
    public Long getCustomerIdByEmail(String email) {
        return customerRepository.findCustomerIdByEmail(email);
    }
    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email) != null;
    }
    public BigDecimal getCurrentBalanceByCustomerId(Long id){//del
        return customerRepository.findCurrentBalanceById(id);
    }
}
