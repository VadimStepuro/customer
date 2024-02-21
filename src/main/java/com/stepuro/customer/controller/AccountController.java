package com.stepuro.customer.controller;

import com.stepuro.customer.api.annotations.Loggable;
import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All found accounts",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AccountDto.class)))}),
            @ApiResponse(responseCode = "404", description = "Accounts not found",
                    content = @Content) })
    @Loggable
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> findAll(){
        List<AccountDto> allAccounts = accountService.findAll();

        return new ResponseEntity<>(allAccounts, HttpStatus.OK);
    }

    @Operation(summary = "Get account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found account by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content) })
    @Loggable
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable("id") UUID id){
        AccountDto foundAccount = accountService.findById(id);

        return new ResponseEntity<>(foundAccount, HttpStatus.OK);
    }

    @Operation(summary = "Create account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid account",
                    content = @Content) })
    @Loggable
    @PostMapping("/accounts")
    public ResponseEntity<AccountDto> create(@RequestBody @Valid AccountDto accountDto){
        AccountDto createdAccount = accountService.create(accountDto);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edited account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid account",
                    content = @Content) })
    @Loggable
    @PutMapping("/accounts")
    public ResponseEntity<AccountDto> edit(@RequestBody @Valid AccountDto accountDto){
        AccountDto editedAccount = accountService.edit(accountDto);

        return new ResponseEntity<>(editedAccount, HttpStatus.OK);
    }

    @Operation(summary = "Delete account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletes account by id",
                    content = { @Content }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content) })
    @Loggable
    @DeleteMapping("/accounts/{id}")
    public void delete(@PathVariable("id") UUID id){
        accountService.delete(id);
    }
}
