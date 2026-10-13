package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public List<User> getAllUsers(Integer adminId){
        return authRepository.findAll();
    }

    //only admin can delete a user
    public void deleteUser(Integer adminId, Integer userId){
        User user = authRepository.findUserById(userId);
        if (user == null){
            throw new ApiException("User not found");
        }

        authRepository.delete(user);
    }

    public List<User> getEmployees(Integer adminId){
        return authRepository.findUserByRole("EMPLOYEE");
    }

    public List<User> getCustomers(Integer adminId){
        return authRepository.findUserByRole("CUSTOMER");
    }


}
