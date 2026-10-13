package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.Model.User;
import com.example.project3.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal User admin){
        return ResponseEntity.status(200).body(authService.getAllUsers(admin.getId()));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User admin, @PathVariable Integer userId){
        authService.deleteUser(admin.getId(), userId);
        return ResponseEntity.status(200).body(new ApiResponse("user deleted"));
    }

    @GetMapping("/get/employees")
    public ResponseEntity<?> getEmployees(@AuthenticationPrincipal User admin){
        return ResponseEntity.status(200).body(authService.getEmployees(admin.getId()));
    }

    @GetMapping("/get/customers")
    public ResponseEntity<?> getCustomers(@AuthenticationPrincipal User admin){
        return ResponseEntity.status(200).body(authService.getCustomers(admin.getId()));
    }
}
