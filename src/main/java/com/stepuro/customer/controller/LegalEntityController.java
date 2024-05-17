package com.stepuro.customer.controller;

import com.stepuro.customer.api.annotations.Loggable;
import com.stepuro.customer.api.dto.LegalEntityDto;
import com.stepuro.customer.service.LegalEntityService;
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

@RestController
@RequestMapping("/api/v1")
public class LegalEntityController {
    private final LegalEntityService legalEntityService;

    public LegalEntityController(LegalEntityService legalEntityService) {
        this.legalEntityService = legalEntityService;
    }

    @Operation(summary = "Get all legal entities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All found legal entities",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = LegalEntityDto.class)))}),
            @ApiResponse(responseCode = "204", description = "Legal entities not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @GetMapping(value = "/legal_entities", produces = "application/json")
    public ResponseEntity<List<LegalEntityDto>> findAll(){
        List<LegalEntityDto> allLegalEntities = legalEntityService.findAll();

        return new ResponseEntity<>(allLegalEntities, HttpStatus.OK);
    }

    @Operation(summary = "Get legal entity by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found legal entity by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LegalEntityDto.class))}),
            @ApiResponse(responseCode = "404", description = "Legal entity not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @GetMapping(value = "/legal_entities/{id}", produces = "application/json")
    public ResponseEntity<LegalEntityDto> findById(@PathVariable("id") Integer id){
        LegalEntityDto foundLegalEntity = legalEntityService.findById(id);

        return new ResponseEntity<>(foundLegalEntity, HttpStatus.OK);
    }

    @Operation(summary = "Create legal entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created legal entity",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LegalEntityDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid legal entity",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class))) })
    @Loggable
    @PostMapping(value = "/legal_entities", produces = "application/json", consumes = "application/json")
    public ResponseEntity<LegalEntityDto> create(@RequestBody @Valid LegalEntityDto legalEntityDto){
        LegalEntityDto createdLegalEntity = legalEntityService.create(legalEntityDto);

        return new ResponseEntity<>(createdLegalEntity, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit legal entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edited legal entity",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LegalEntityDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid legal entity",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class))),
            @ApiResponse(responseCode = "404", description = "Legal entity not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @Loggable
    @PutMapping(value = "/legal_entities", produces = "application/json", consumes = "application/json")
    public ResponseEntity<LegalEntityDto> edit(@RequestBody @Valid LegalEntityDto legalEntityDto){
        LegalEntityDto editedLegalEntity = legalEntityService.edit(legalEntityDto);

        return new ResponseEntity<>(editedLegalEntity, HttpStatus.OK);
    }

    @Operation(summary = "Delete legal entity by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletes legal entity by id",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Legal entity not found",
                    content = @Content(mediaType = "application/json")) })
    @Loggable
    @DeleteMapping(value = "/legal_entities/{id}", produces = "application/json")
    public void delete(@PathVariable("id") Integer id){
        legalEntityService.delete(id);
    }
}
