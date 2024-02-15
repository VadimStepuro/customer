package com.stepuro.customer.controller;

import com.stepuro.customer.api.annotations.Loggable;
import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Loggable
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> findAll(){
        List<AccountDto> allAccounts = accountService.findAll();

        if(allAccounts.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(allAccounts, HttpStatus.OK);
    }

    @Loggable
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable("id") UUID id){
        AccountDto foundAccount = accountService.findById(id);

        return new ResponseEntity<>(foundAccount, HttpStatus.OK);
    }

    @Loggable
    @PostMapping("/accounts")
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto){
        AccountDto createdAccount = accountService.create(accountDto);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @Loggable
    @PutMapping("/accounts")
    public ResponseEntity<AccountDto> edit(@RequestBody AccountDto accountDto){
        AccountDto editedAccount = accountService.edit(accountDto);

        return new ResponseEntity<>(editedAccount, HttpStatus.OK);
    }

    @Loggable
    @DeleteMapping("/accounts/{id}")
    public void delete(@PathVariable("id") UUID id){
        accountService.delete(id);
    }
}
