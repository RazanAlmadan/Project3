package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity<?> getCustomerInfo(@AuthenticationPrincipal Customer customer){
        return ResponseEntity.status(200).body(customerService.getCustomerInfo(customer.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> register(@RequestBody @Valid CustomerDTO customerDTO){
        customerService.register(customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("customer added"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid CustomerDTO customerDTO){
        customerService.updateCustomer(user.getId(), customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("customer updated"));
    }


}
