package com.jw.vacationbalance.service;

import static com.jw.vacationbalance.entity.employee.Role.HOURLY;
import static org.assertj.core.api.Assertions.assertThat;

import com.jw.vacationbalance.dao.EmployeeDao;
import com.jw.vacationbalance.dto.EmployeeRequestDto;
import com.jw.vacationbalance.entity.employee.AbstractEmployee;
import com.jw.vacationbalance.entity.employee.HourlyEmployee;
import com.jw.vacationbalance.entity.employee.ManagerEmployee;
import com.jw.vacationbalance.entity.employee.SalariedEmployee;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)

public class EmployeeServiceTest {

    @Mock
    private EmployeeDao dao;

    @InjectMocks
    private EmployeeService employeeService;

    private AbstractEmployee expectedEmployee0;
    private AbstractEmployee expectedEmployee1;

    private List<AbstractEmployee> expectedEmployeeList;

    private final EmployeeRequestDto employeeDto = new EmployeeRequestDto(10000l, "Jesse", HOURLY);

    @BeforeEach
    public void setup(){
        expectedEmployee0 = new HourlyEmployee(42, "Sal");
        expectedEmployee1 = new SalariedEmployee(43, "Walt");

        expectedEmployeeList = new ArrayList<>();

        expectedEmployeeList.add(expectedEmployee0);
        expectedEmployeeList.add(expectedEmployee1);
    }

    @DisplayName("'getAll' employees test")
    @Test
    public void givenEmployees_whenGetAll_thenReturnEmployeeList(){
        // given
        given(dao.getAll())
                .willReturn(expectedEmployeeList);

        // when
        List<AbstractEmployee> actualEmployees = employeeService.getAll();

        // then
        assertThat(actualEmployees).contains(expectedEmployee0);
        assertThat(actualEmployees).hasSize(2);
    }

    @DisplayName("'getOne' employee (by id) test")
    @Test
    public void givenEmployeeId_whenGetOne_thenReturnEmployee() throws ResourceNotFoundException {
        // given
        given(dao.getOne(42l)).willReturn(expectedEmployee0);

        // when
        AbstractEmployee savedEmployee = employeeService.getOne(expectedEmployee0.getId());

        // then
        assertThat(savedEmployee.getName()).isEqualTo("Sal");
    }

    @DisplayName("'addOne' employee test")
    @Test
    public void givenEmployeeDto_whenAddOne_thenSaveEmployee() throws ResourceNotFoundException {
        // given
        given(dao.addOne(employeeDto)).willReturn(12000l);

        // when
        long newId = employeeService.addOne(employeeDto);

        // then
        verify(dao, times(1)).addOne(employeeDto);
    }

    @DisplayName("'updateOne' employee test")
    @Test
    public void givenEmployeeDto_whenUpdateOne_thenUpdateEmployee() throws ResourceNotFoundException {
        // given
        doNothing().when(dao).updateOne(isA(EmployeeRequestDto.class));

        // when
        employeeService.updateOne(employeeDto);

        // then
        verify(dao, times(1)).updateOne(employeeDto);
    }

    @DisplayName("'deleteOne' employee by id test")
    @Test
    public void givenEmployeeId_whenDeleteOne_thenDeleteEmployee() throws ResourceNotFoundException {
        // given
        doNothing().when(dao).deleteOne(anyLong());

        // when
        employeeService.deleteOne(99l);

        // then
        verify(dao, times(1)).deleteOne(99l);
    }
}
