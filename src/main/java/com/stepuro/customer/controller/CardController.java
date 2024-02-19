package com.stepuro.customer.controller;

import com.stepuro.customer.api.annotations.Loggable;
import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.service.CardService;
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
public class CardController {
    @Autowired
    private CardService cardService;

    @Operation(summary = "Get all cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All found cards",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CardDto.class)))}),
            @ApiResponse(responseCode = "404", description = "Cards not found",
                    content = @Content) })
    @Loggable
    @GetMapping("/cards")
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
                    content = @Content) })
    @Loggable
    @GetMapping("/cards/{id}")
    public ResponseEntity<CardDto> findById(@PathVariable("id") UUID id){
        CardDto foundCard = cardService.findById(id);

        return new ResponseEntity<>(foundCard, HttpStatus.OK);
    }

    @Operation(summary = "Create card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created card",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid card",
                    content = @Content) })
    @Loggable
    @PostMapping("/cards")
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
                    content = @Content) })
    @Loggable
    @PutMapping("/cards")
    public ResponseEntity<CardDto> edit(@RequestBody @Valid CardDto cardDto){
        CardDto editedCard = cardService.edit(cardDto);

        return new ResponseEntity<>(editedCard, HttpStatus.OK);
    }

    @Operation(summary = "Delete card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletes card by id",
                    content = { @Content }),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content) })
    @Loggable
    @DeleteMapping("/cards/{id}")
    public void delete(@PathVariable("id") UUID id){
        cardService.delete(id);
    }
}