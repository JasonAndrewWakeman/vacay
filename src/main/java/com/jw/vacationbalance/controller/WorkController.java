package com.jw.vacationbalance.controller;

import com.jw.vacationbalance.dto.WorkDto;
import com.jw.vacationbalance.exception.BadRequestException;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import com.jw.vacationbalance.service.WorkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Work Controller", description = "API to manage employee statements of work")
public class WorkController {

    @Autowired
    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    /**
     * Adds a statement of work for a given employee with a given integer for days worked
     *
     * @param workDto the data transfer object representing a statement of work
     *
     * @return statementId (long) The id of the statement of work
     *
     * @throws BadRequestException
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/work-statement/", method = RequestMethod.POST)
    @Operation(summary = "Post a statement of work consisting of employeeId (long), and workedDays (int)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "returns the id of the created work statement",
                    content = {@Content(schema = @Schema(example = "328324"))}),
            @ApiResponse(responseCode = "400", description = "work days is invalid quantity",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
            @ApiResponse(responseCode = "404", description = "no employee with the given id",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = {@Content(schema = @Schema(implementation =  String.class))})
    })
    public @ResponseBody ResponseEntity<Long> addOne(@RequestBody WorkDto workDto)
            throws ResourceNotFoundException, BadRequestException {
        Long statementId = workService.processWork(workDto);
        return new ResponseEntity(statementId, HttpStatus.CREATED);
    }
}
