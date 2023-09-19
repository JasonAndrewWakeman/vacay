package com.jw.vacationbalance.controller;

import com.jw.vacationbalance.dto.EmployeeRequestDto;
import com.jw.vacationbalance.entity.employee.AbstractEmployee;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import com.jw.vacationbalance.service.EmployeeService;
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

import java.util.List;
@RestController
@Tag(name = "Employee Controller", description = "API to access Employees to facilitate vacation management")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    public final static String SUCCESSFUL_UPDATE_MSG = "Employee updated";
    public final static String SUCCESSFUL_DELETE_MSG = "Employee deleted";

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Fetch all employees.
     *
     * @return a list of all employees wrapped in 200 response entity.
     */
    @Operation(summary = "Returns a list of all saved employees")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = AbstractEmployee.class),
                            mediaType = "application/json") },
                    description = "returns a list of AbstractEmployees"),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = {@Content(schema = @Schema(implementation =  String.class))})})
    @RequestMapping(value = "/employees/", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<AbstractEmployee>> getAll() {
        List<AbstractEmployee> employees = employeeService.getAll();
        return new ResponseEntity(employees, HttpStatus.OK);
    }

    /**
     * Fetch one employee.
     *
     * @return a single employee with id
     */
    @Operation(summary = "Returns the employee with given id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = AbstractEmployee.class),
                            mediaType = "application/json") },
                    description = "returns an AbstractEmployees"),
            @ApiResponse(responseCode = "404", description = "No employee with id exists",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
    })
    @RequestMapping(value = "/employee/{id}/", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<AbstractEmployee> getOne(@PathVariable long id) throws ResourceNotFoundException {
        AbstractEmployee employee = employeeService.getOne(id);
        return new ResponseEntity(employee, HttpStatus.OK);
    }


    /**
     * Add an employee.
     *
     * Provide the employee's name and role.
     *
     * @param employeeDto JSON with role and name keys
     * @return the id of the newly created employee
     */
    @Operation(summary = "Saves an employee from an employeeRequestDto", description = "request requires 'name' and the 'role' enum.  id is ignored and can be null")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = { @Content(schema = @Schema(example = "3288989828")) },
                    description = "returns the id of the newly created employee"),
            @ApiResponse(responseCode = "400", description = "Make sure you are including the name an valid value for role",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
    })
    @RequestMapping(value = "/employee/", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Long> addOne(@RequestBody EmployeeRequestDto employeeDto) {
        Long newId = employeeService.addOne(employeeDto);
        return new ResponseEntity(newId, HttpStatus.CREATED);
    }

    /**
     * Update a user.
     *
     * Only the employee's name and/or role can be changed.
     *
     * @param employeeDto JSON with role and name keys
     * @return a 204 response with success message
     */
    @Operation(summary = "Updates an employee from an employeeRequestDto", description = "Will override 'name' and the 'role' enum with values in request for the employee with given id")
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    content = {@Content(schema = @Schema(implementation =  String.class))},
                    description = "Success, nothing to return"),
            @ApiResponse(responseCode = "400", description = "Make sure you are including the name an valid value for role",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
            @ApiResponse(responseCode = "404", description = "No employee with id exists",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
    })
    @RequestMapping(value = "/employee/", method = RequestMethod.PATCH)
    public @ResponseBody ResponseEntity<String> updateOne(@RequestBody EmployeeRequestDto employeeDto) throws ResourceNotFoundException {
        employeeService.updateOne(employeeDto);
        return new ResponseEntity(SUCCESSFUL_UPDATE_MSG, HttpStatus.NO_CONTENT);
    }


    /**
     * Delete an employee by id.
     *
     * @param id The id of the employee to delete.
     * @return a 204 response with success message.
     */
    @Operation(summary = "Deletes employee with given id")
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    content = {@Content(schema = @Schema(implementation =  String.class))},
                    description = "Success, nothing to return"),
            @ApiResponse(responseCode = "404", description = "No employee with id exists",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
            @ApiResponse(responseCode = "500", description = "server error",
                    content = {@Content(schema = @Schema(implementation =  String.class))}),
    })
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<String> deleteOne(@PathVariable Long id) throws ResourceNotFoundException {
        employeeService.deleteOne(id);
        return new ResponseEntity(SUCCESSFUL_DELETE_MSG, HttpStatus.NO_CONTENT);
    }
}
