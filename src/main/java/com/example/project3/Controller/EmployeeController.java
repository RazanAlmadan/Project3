package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<?> getEmployeeInfo(@AuthenticationPrincipal Employee employee){
        return ResponseEntity.status(200).body(employeeService.getEmployeeInfo(employee.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid EmployeeDTO employeeDTO){
        employeeService.register(employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("employee added"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@AuthenticationPrincipal Employee employee, @RequestBody @Valid EmployeeDTO employeeDTO){
        employeeService.updateEmployee(employee.getId(), employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("employee updated"));
    }
}
