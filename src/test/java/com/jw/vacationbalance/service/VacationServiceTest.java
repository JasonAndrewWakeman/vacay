package com.jw.vacationbalance.service;

import com.jw.vacationbalance.dao.EmployeeDao;
import com.jw.vacationbalance.dto.VacationDto;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.isA;

@ExtendWith(MockitoExtension.class)

public class VacationServiceTest {

    @Mock
    private EmployeeDao dao;

    @InjectMocks
    private VacationService vacationService;

    VacationDto vacationDto = new VacationDto(5000l, 0);
    AbstractEmployee employee;

    @BeforeEach
    public void setup() {
        employee = new HourlyEmployee(vacationDto.getEmployeeId(), "Mel");
    }

    @DisplayName("'processVacation' vacationStatement success test")
    @Test
    public void givenVacationStatementForValidEmployee_whenProcessVacation_thenStatementIdIsReturned() throws BadRequestException, ResourceNotFoundException {
        // given
        long expectedVacationStatementId = 54l;
        given(dao.getOne(employee.getId()))
                .willReturn(employee);
        doNothing().when(dao).updateOne(isA(AbstractEmployee.class));

        given(dao.addVacationStatement(vacationDto.getEmployeeId(), vacationDto.getVacationDays()))
                .willReturn(expectedVacationStatementId);

        // when
        long actualVacationStatementId = vacationService.processVacation(vacationDto);

        // then
        assertThat(actualVacationStatementId).isEqualTo(expectedVacationStatementId);
    }


    @DisplayName("'processVacation' < 0 throws test")
    @Test
    public void givenBadVacationDto_whenProcessVacation_thenThrowsException() throws ResourceNotFoundException {
        // given
        VacationDto badVacationDto = new VacationDto(25l, -1f);
        long vacationStatementId = -1l;
        given(dao.getOne(badVacationDto.getEmployeeId()))
                .willReturn(employee);
        // when
        try {
            vacationStatementId = vacationService.processVacation(badVacationDto);
        } catch (BadRequestException e) {
            assertThat(e).hasMessage("Vacation days must be between 0 and the amount currently available (0.0)");
        }
        // then
        assertThat(vacationStatementId).isEqualTo(-1l);
    }

    @DisplayName("'processVacation' > balance throws test")
    @Test
    public void givenTooBigVacationDto_whenProcessVacation_thenThrowsException() throws ResourceNotFoundException {
        // given
        VacationDto badVacationDto = new VacationDto(25l, 350f);
        long vacationStatementId = -1l;
        given(dao.getOne(badVacationDto.getEmployeeId()))
                .willReturn(employee);
        // when
        try {
            vacationStatementId = vacationService.processVacation(badVacationDto);
        } catch (BadRequestException e) {
            assertThat(e).hasMessage("Vacation days must be between 0 and the amount currently available (0.0)");
        }
        // then
        assertThat(vacationStatementId).isEqualTo(-1l);
    }

    @DisplayName("'computeDecreasedVacation' test")
    @Test
    public void givenValidVacationStatementForEmployee_whenComputeDecreasedVacation_thenNewBalanceIsCorrect() throws BadRequestException, ResourceNotFoundException {
        // given
        float expectedNewBalance = 300f - vacationDto.getVacationDays();

        // when
        float actualDecreasedVacation = vacationService.computeDecreasedVacation(300, vacationDto);

        // then
        assertThat(actualDecreasedVacation).isEqualTo(expectedNewBalance);
    }
}