package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/add")
    public ResponseEntity<?> addAccount(@AuthenticationPrincipal User user){
        accountService.addAccount(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("account added"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllAccounts(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(accountService.getAllAccounts(user.getId()));
    }

    @GetMapping("/get/{accountId}")
    public ResponseEntity<?> getAccountDetails(@AuthenticationPrincipal User user, @PathVariable Integer accountId){
        return ResponseEntity.status(200).body(accountService.getAccountDetails(user.getId(), accountId));
    }

    @GetMapping("/my/accounts")
    public ResponseEntity<?> getUserAccounts(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(accountService.getUserAccounts(user.getId()));
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<?> updateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestBody Account account){
        accountService.updateAccount(user.getId(), accountId, account);
        return ResponseEntity.status(200).body(new ApiResponse("account updated"));
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId){
        accountService.deleteAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("account deleted"));
    }

    @PutMapping("/activate/{accountId}")
    public ResponseEntity<?> activateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId){
        accountService.activateAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("account activated"));
    }

    @PutMapping("/deposit/{accountId}")
    public ResponseEntity<?> deposit(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestParam Double amount){
        accountService.deposit(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("deposit successful"));
    }

    @PutMapping("/withdraw/{accountId}")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestParam Double amount){
        accountService.withdraw(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("withdrawal successful"));
    }

    @PutMapping("/transfer/{fromAccountId}/{toAccountId}")
    public ResponseEntity<?> transfer(@AuthenticationPrincipal User user, @PathVariable Integer fromAccountId, @PathVariable Integer toAccountId, @RequestParam Double amount){
        accountService.transfer(user.getId(), fromAccountId, toAccountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("transfer successful"));
    }

    @PutMapping("/block/{accountId}")
    public ResponseEntity<?> blockAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId){
        accountService.blockAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("account blocked"));
    }
}
