package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final AuthRepository authRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeDTO getEmployeeInfo(Integer employeeId){
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null){
            throw new ApiException("no employee found");
        }
        User user = authRepository.findUserById(employeeId);
        EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), employee.getPosition(), employee.getSalary());

        return employeeDTO;
    }

    public void register(EmployeeDTO employeeDTO){
        User user = new User();
        user.setName(employeeDTO.getName());
        user.setUsername(employeeDTO.getUsername());
        user.setEmail(employeeDTO.getEmail());
        user.setPassword(employeeDTO.getPassword());
        user.setRole("EMPLOYEE");
        authRepository.save(user);

        Employee employee = new Employee(null, employeeDTO.getPosition(), employeeDTO.getSalary(), user);
        employeeRepository.save(employee);

    }

    public void updateEmployee(Integer userId, EmployeeDTO employeeDTO){
        Employee employee = employeeRepository.findEmployeeById(userId);
        if (employee == null){
            throw new ApiException("employee not found");
        }

        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        User user = authRepository.findUserById(userId);
        user.setName(employeeDTO.getName());
        user.setUsername(employeeDTO.getUsername());
        user.setEmail(employeeDTO.getEmail());
        authRepository.save(user);
    }

    // delete in authService for admin only
}
