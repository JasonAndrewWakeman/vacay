package com.jw.vacationbalance.controller;

import com.jw.vacationbalance.dto.VacationDto;
import com.jw.vacationbalance.exception.BadRequestException;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import com.jw.vacationbalance.service.VacationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Vacation Controller", description = "API to manage employee vacation issuance")
public class VacationController {

    @Autowired
    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    /**
     * Adds a vacation issuance statement for a given employee with a given float for vacation time in days.
     *
     * @param vacationDto the data transfer object representing a vacation statement
     *
     * @return statementId (long) The id of the statement of vacation issuance
     *
     * @throws BadRequestException
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Post a vacation statement consisting of employeeId (long), and vacation days (float)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "returns the id of the created vacation statement",
                    content = {@Content(schema = @Schema(example = "328324"))}),
            @ApiResponse(responseCode = "400", description = "vacation days is invalid amount",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
            @ApiResponse(responseCode = "404", description = "no employee with the given id",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
    })
    @RequestMapping(value = "/vacation-statement/", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Long> addOne(@Valid @RequestBody VacationDto vacationDto)
            throws BadRequestException, ResourceNotFoundException {
        long statementId = vacationService.processVacation(vacationDto);
        return new ResponseEntity(statementId, HttpStatus.CREATED);
    }
}
