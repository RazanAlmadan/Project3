package com.example.project3.Service;

import com.example.project3.Model.Account;
import com.example.project3.Repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void addAccount(Integer userId, Account account){

    }
}
