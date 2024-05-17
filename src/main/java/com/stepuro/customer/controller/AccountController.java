package com.stepuro.customer.controller;

import com.stepuro.customer.api.annotations.Loggable;
import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.service.AccountService;
import com.stepuro.customer.service.TransferAmountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    private final AccountService accountService;
    private final TransferAmountService transferAmountService;

    public AccountController(AccountService accountService, TransferAmountService transferAmountService) {
        this.accountService = accountService;
        this.transferAmountService = transferAmountService;
    }

    @Operation(summary = "Get all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All found accounts",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AccountDto.class)))}),
            @ApiResponse(responseCode = "204", description = "Accounts not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @GetMapping(value = "/accounts", produces = "application/json")
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
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @GetMapping(value = "/accounts/{id}", produces = "application/json")
    public ResponseEntity<AccountDto> findById(@PathVariable("id") UUID id){
        AccountDto foundAccount = accountService.findById(id);

        return new ResponseEntity<>(foundAccount, HttpStatus.OK);
    }

    @Operation(summary = "Get account by account number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found account by account number",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @GetMapping(value = "/accounts/get_by_account_number/{accountNumber}", produces = "application/json")
    public ResponseEntity<AccountDto> findByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        AccountDto foundAccount = accountService.findByNumber(accountNumber);

        return new ResponseEntity<>(foundAccount, HttpStatus.OK);
    }

    @Operation(summary = "Transfer amount from one account to another")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer was done",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema)}),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Transfer isn't valid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, description = "Reason why transfer isn't valid")))})
    @Loggable
    @PutMapping(value = "/accounts/transfer_amount", produces = "application/json", consumes = "application/json")
    public void transferAmount(@RequestBody @Valid TransferEntity transferEntity){
        transferAmountService.transferAccountAmount(transferEntity);
    }

    @Operation(summary = "Create account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid account",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class))) })
    @Loggable
    @PostMapping(value = "/accounts", consumes = "application/json", produces = "application/json")
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
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class))) })
    @Loggable
    @PutMapping(value = "/accounts", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AccountDto> edit(@RequestBody @Valid AccountDto accountDto){
        AccountDto editedAccount = accountService.edit(accountDto);

        return new ResponseEntity<>(editedAccount, HttpStatus.OK);
    }

    @Operation(summary = "Delete account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletes account by id",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content) })
    @Loggable
    @DeleteMapping(value = "/accounts/{id}", produces = "application/json")
    public void delete(@PathVariable("id") UUID id){
        accountService.delete(id);
    }
}
