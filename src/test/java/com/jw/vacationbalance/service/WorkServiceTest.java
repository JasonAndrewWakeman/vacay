package com.jw.vacationbalance.service;

import com.jw.vacationbalance.dao.EmployeeDao;
import com.jw.vacationbalance.dto.WorkDto;
import com.jw.vacationbalance.entity.employee.AbstractEmployee;
import com.jw.vacationbalance.entity.employee.HourlyEmployee;
import com.jw.vacationbalance.exception.BadRequestException;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class WorkServiceTest {

    @Mock
    private EmployeeDao dao;

    @InjectMocks
    private WorkService workService;

    WorkDto workDto = new WorkDto(25l, 260);
    AbstractEmployee employee;

    @BeforeEach
    public void setup(){
        employee = new HourlyEmployee(workDto.getEmployeeId(), "Jesse");
    }

    @DisplayName("'processWork' workStatement success test")
    @Test
    public void givenWorkStatementForValidEmployee_whenProcessWork_thenStatementIdIsReturned() throws BadRequestException, ResourceNotFoundException {
        // given
        long expectedWorkStatementId = 27l;
        given(dao.getOne(employee.getId()))
                .willReturn(employee);
        doNothing().when(dao).updateOne(isA(AbstractEmployee.class));

        given(dao.addWorkStatement(workDto.getEmployeeId(), workDto.getWorkedDays()))
                .willReturn(expectedWorkStatementId);

        // when
        long actualWorkStatementId = workService.processWork(workDto);

        // then
        assertThat(actualWorkStatementId).isEqualTo(expectedWorkStatementId);
    }


    @DisplayName("'processWork' more than allowed throws test")
    @Test
    public void givenTooBigWorkDto_whenProcessWork_thenThrowsException() throws ResourceNotFoundException {
        // given
        WorkDto badWorkDto = new WorkDto(25l, 261);
        long workStatementId = -1l;

        // when
        try {
            workStatementId = workService.processWork(badWorkDto);
        }
        catch (BadRequestException e) {
            assertThat(e).hasMessage("Days worked must be between 0 and 260");
        }
        // then
        assertThat(workStatementId).isEqualTo(-1l);
    }

    @DisplayName("'processWork' less than 0 throws test")
    @Test
    public void givenTooSmallWorkDto_whenProcessWork_thenThrowsException() throws ResourceNotFoundException {
        // given
        WorkDto badWorkDto = new WorkDto(25l, -1);
        long workStatementId = -1l;

        // when
        try {
            workStatementId = workService.processWork(badWorkDto);
        }
        catch (BadRequestException e) {
            assertThat(e).hasMessage("Days worked must be between 0 and 260");
        }
        // then
        assertThat(workStatementId).isEqualTo(-1l);
    }


    @DisplayName("'computeIncreasedVacation' test")
    @Test
    public void givenWorkStatementForValidEmployee_whenComputeIncreasedVacation_thenNewBalanceIsCorrect() throws BadRequestException, ResourceNotFoundException {
        // given
        float expectedNewBalance = employee.getVacationAccrualRate() * workDto.getWorkedDays();

        // when
        float actualIncreasedVacation = workService.computeIncreasedVacation(employee, workDto.getWorkedDays());

        // then
        assertThat(actualIncreasedVacation).isEqualTo(expectedNewBalance);
    }
}
