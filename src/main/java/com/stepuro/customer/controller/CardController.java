package com.stepuro.customer.controller;

import com.stepuro.customer.api.annotations.Loggable;
import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.service.CardService;
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
public class CardController {
    private final CardService cardService;
    private final TransferAmountService transferAmountService;

    public CardController(CardService cardService, TransferAmountService transferAmountService) {
        this.cardService = cardService;
        this.transferAmountService = transferAmountService;
    }

    @Operation(summary = "Get all cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All found cards",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CardDto.class)))}),
            @ApiResponse(responseCode = "204", description = "Cards not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @GetMapping(value = "/cards", produces = "application/json")
    public ResponseEntity<List<CardDto>> findAll(){
        List<CardDto> allCards = cardService.findAll();

        return new ResponseEntity<>(allCards, HttpStatus.OK);
    }

    @Operation(summary = "Get card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found card by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDto.class))}),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @GetMapping(value = "/cards/{id}", produces = "application/json")
    public ResponseEntity<CardDto> findById(@PathVariable("id") UUID id){
        CardDto foundCard = cardService.findById(id);

        return new ResponseEntity<>(foundCard, HttpStatus.OK);
    }

    @Operation(summary = "Get card by card number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found card by card number",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDto.class))}),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @GetMapping(value = "/cards/get_by_card_number/{cardNumber}", produces = "application/json")
    public ResponseEntity<CardDto> findByAccountNumber(@PathVariable("cardNumber") String cardNumber){
        CardDto foundCard = cardService.findByNumber(cardNumber);

        return new ResponseEntity<>(foundCard, HttpStatus.OK);
    }

    @Operation(summary = "Transfer amount from one card to another")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer is done",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema)}),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Transfer isn't valid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, description = "Reason why transfer isn't valid")))})
    @Loggable
    @PutMapping(value = "/cards/transfer_amount", produces = "application/json", consumes = "application/json")
    public void transferAmount(@RequestBody TransferEntity transferEntity){
        transferAmountService.transferCardAmount(transferEntity);
    }

    @Operation(summary = "Create card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created card",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid card",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class))) })
    @Loggable
    @PostMapping(value = "/cards", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CardDto> create(@RequestBody @Valid CardDto cardDto){
        CardDto createdCard = cardService.create(cardDto);

        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edited card",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid card",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class))),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @PutMapping(value = "/cards", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CardDto> edit(@RequestBody @Valid CardDto cardDto){
        CardDto editedCard = cardService.edit(cardDto);

        return new ResponseEntity<>(editedCard, HttpStatus.OK);
    }

    @Operation(summary = "Delete card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletes card by id",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @DeleteMapping(value = "/cards/{id}", produces = "application/json")
    public void delete(@PathVariable("id") UUID id){
        cardService.delete(id);
    }
}
