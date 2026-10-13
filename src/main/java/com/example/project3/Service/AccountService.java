package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;

    public void addAccount(Integer userId){
        User user = authRepository.findUserById(userId);
        if (user == null){
            throw new ApiException("user not found");
        }

        Account account = new Account();

        // generate random number for account number when creating an account
        Random random = new Random();
        account.setAccountNumber(String.format("%04d-%04d-%04d-%04d",
                random.nextInt(10000),
                random.nextInt(10000),
                random.nextInt(10000),
                random.nextInt(10000)));

        account.setBalance(0.0);
        account.setIsActive(false);
        account.setUser(user);

        accountRepository.save(account);
    }

    public List<Account> getAllAccounts(Integer adminId){
        return accountRepository.findAll();
    }

    public Account getAccountDetails(Integer userId, Integer accountId){
        Account account = accountRepository.findAccountById(accountId);
        if (account == null){
            throw new ApiException("account not found");
        }

        if (!account.getUser().getId().equals(userId)){
            throw new ApiException("you are not allowed to view this account");
        }

        return account;
    }

    public List<Account> getUserAccounts(Integer userId){
        return accountRepository.findAccountsByUserId(userId);
    }

    public void updateAccount(Integer userId, Integer accountId, Account accountDetails){
        Account account = accountRepository.findAccountById(accountId);
        if (account == null){
            throw new ApiException("account not found");
        }

        if (!account.getUser().getId().equals(userId)){
            throw new ApiException("you are not allowed to access this account");
        }

        account.setIsActive(accountDetails.getIsActive());
        accountRepository.save(account);
    }

    public void deleteAccount(Integer userId, Integer accountId){
        Account account = accountRepository.findAccountById(accountId);
        if (account == null){
            throw new ApiException("account not found");
        }

        if (!account.getUser().getId().equals(userId)){
            throw new ApiException("you are not allowed to access this account");
        }
        // you can't delete an account that has money in it
        if (account.getBalance() != null && account.getBalance() != 0.0){
            throw new ApiException("account balance must be zero before deleting");
        }

        accountRepository.delete(account);
    }

    public void activateAccount(Integer userId, Integer accountId){
        Account account = accountRepository.findAccountById(accountId);
        if (account == null){
            throw new ApiException("account not found");
        }

        if (!account.getUser().getId().equals(userId)){
            throw new ApiException("you are not allowed to access this account");
        }

        account.setIsActive(true);
        accountRepository.save(account);
    }

    public void deposit(Integer userId, Integer accountId, Double amount){
        Account account = accountRepository.findAccountById(accountId);
        if (account == null){
            throw new ApiException("account not found");
        }

        if (!account.getUser().getId().equals(userId)){
            throw new ApiException("you are not allowed to access this account");
        }

        if (account.getIsActive() == false){
            throw new ApiException("account is not active");
        }

        if (amount == null || amount <= 0){
            throw new ApiException("amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdraw(Integer userId, Integer accountId, Double amount){
        Account account = accountRepository.findAccountById(accountId);
        if (account == null){
            throw new ApiException("account not found");
        }

        if (!account.getUser().getId().equals(userId)){
            throw new ApiException("you are not allowed to access this account");
        }

        if (account.getIsActive() == false){
            throw new ApiException("account is not active");
        }

        if (amount == null || amount <= 0){
            throw new ApiException("amount must be greater than zero");
        }

        if (account.getBalance() < amount){
            throw new ApiException("insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void transfer(Integer userId, Integer fromAccountId, Integer toAccountId, Double amount){
        if (fromAccountId.equals(toAccountId)){
            throw new ApiException("cannot transfer to the same account");
        }

        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);

        if (fromAccount == null || toAccount == null){
            throw new ApiException("account not found");
        }

        if (!fromAccount.getUser().getId().equals(userId)){
            throw new ApiException("you are not allowed to access this account");
        }

        if (fromAccount.getIsActive() == false || toAccount.getIsActive() == false){
            throw new ApiException("both accounts must be active");
        }

        if (amount == null || amount <= 0){
            throw new ApiException("amount must be greater than zero");
        }

        if (fromAccount.getBalance() < amount){
            throw new ApiException("insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public void blockAccount(Integer adminId, Integer accountId){
        Account account = accountRepository.findAccountById(accountId);
        if (account == null){
            throw new ApiException("account not found");
        }

        account.setIsActive(false);
        accountRepository.save(account);
    }
}
