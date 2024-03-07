package com.stepuro.customer.controller;

import com.stepuro.customer.api.annotations.Loggable;
import com.stepuro.customer.api.dto.ApiError;
import com.stepuro.customer.api.dto.PaymentOrderEntityDto;
import com.stepuro.customer.service.TransferAmountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TransferAmountController {
    @Autowired
    private TransferAmountService transferAmountService;

    @Operation(summary = "Transfer amount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sends transfer",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Payment order entity is invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
    @Loggable
    @PutMapping(value = "/transfer_amounts", produces = "application/json", consumes = "application/json")
    public void transferAmount(@RequestBody @Valid PaymentOrderEntityDto paymentOrderEntityDto){
        transferAmountService.transferAmount(paymentOrderEntityDto);
    }
}
