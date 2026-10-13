package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public CustomerDTO getCustomerInfo(Integer customerId){
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null){
            throw new ApiException("no customer found");
        }
        User user = authRepository.findUserById(customerId);
        CustomerDTO customerDTO = new CustomerDTO(customerId, user.getUsername(), null, user.getName(), user.getEmail(), customer.getPhoneNumber());
        return customerDTO;
    }

    public void register(CustomerDTO customerDTO){
        User user = new User();
        user.setName(customerDTO.getName());
        user.setUsername(customerDTO.getUsername());
        user.setEmail(customerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        user.setRole("CUSTOMER");
        authRepository.save(user);

        Customer customer = new Customer(null, customerDTO.getPhoneNumber(), user);
        customerRepository.save(customer);
    }

    public void updateCustomer(Integer userId, CustomerDTO customerDTO){
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null){
            throw new ApiException("customer not found");
        }

        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customerRepository.save(customer);
        User user = authRepository.findUserById(userId);
        user.setName(customerDTO.getName());
        user.setUsername(customerDTO.getUsername());
        user.setEmail(customerDTO.getEmail());
        authRepository.save(user);
    }


    // delete in authService for admin only
}
