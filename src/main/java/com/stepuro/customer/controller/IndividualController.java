package com.stepuro.customer.controller;

import com.stepuro.customer.api.annotations.Loggable;
import com.stepuro.customer.api.dto.IndividualDto;
import com.stepuro.customer.service.IndividualService;
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

@RestController
@RequestMapping("/api/v1")
public class IndividualController {
    @Autowired
    private IndividualService individualService;

    @Operation(summary = "Get all individuals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All found individuals",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = IndividualDto.class)))}),
            @ApiResponse(responseCode = "404", description = "Individuals not found",
                    content = @Content) })
    @Loggable
    @GetMapping("/individuals")
    public ResponseEntity<List<IndividualDto>> findAll(){
        List<IndividualDto> allIndividuals = individualService.findAll();

        return new ResponseEntity<>(allIndividuals, HttpStatus.OK);
    }

    @Operation(summary = "Get individual by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found individual by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IndividualDto.class))}),
            @ApiResponse(responseCode = "404", description = "Individual not found",
                    content = @Content) })
    @Loggable
    @GetMapping("/individuals/{id}")
    public ResponseEntity<IndividualDto> findById(@PathVariable("id") Integer id){
        IndividualDto foundIndividual = individualService.findById(id);

        return new ResponseEntity<>(foundIndividual, HttpStatus.OK);
    }

    @Operation(summary = "Create individual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created individual",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IndividualDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid individual",
                    content = @Content) })
    @Loggable
    @PostMapping("/individuals")
    public ResponseEntity<IndividualDto> create(@RequestBody @Valid IndividualDto individualDto){
        IndividualDto createdIndividual = individualService.create(individualDto);

        return new ResponseEntity<>(createdIndividual, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit individual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edited individual",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IndividualDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid individual",
                    content = @Content) })
    @Loggable
    @PutMapping("/individuals")
    public ResponseEntity<IndividualDto> edit(@RequestBody @Valid IndividualDto individualDto){
        IndividualDto editedIndividual = individualService.edit(individualDto);

        return new ResponseEntity<>(editedIndividual, HttpStatus.OK);
    }

    @Operation(summary = "Delete individual by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletes individual by id",
                    content = { @Content}),
            @ApiResponse(responseCode = "404", description = "Individual not found",
                    content = @Content) })
    @Loggable
    @DeleteMapping("/individuals/{id}")
    public void delete(@PathVariable("id") Integer id){
        individualService.delete(id);
    }
}
