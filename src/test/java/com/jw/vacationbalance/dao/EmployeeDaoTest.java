package com.jw.vacationbalance.dao;

import com.jw.vacationbalance.dto.EmployeeRequestDto;
import com.jw.vacationbalance.entity.employee.AbstractEmployee;
import com.jw.vacationbalance.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.jw.vacationbalance.entity.employee.Role.*;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class EmployeeDaoTest {

    @Mock
    private Uuid uuid;

    @InjectMocks
    private EmployeeDao employeeDao;

    @BeforeEach
    public void setup() {}

    @DisplayName("'getAll' employees test")
    @Test
    public void givenEmployees_whenGetAll_thenReturnEmployeeList() {
        // given
        EmployeeRequestDto employeeDto1000 = new EmployeeRequestDto(1000l, "Jesse", HOURLY);
        employeeDao.addOne(employeeDto1000);

        // when
        List<AbstractEmployee> actualEmployees = employeeDao.getAll();

        // then
        assertThat(actualEmployees).isNotEmpty(); // note it could be seeded or not so can contain 31 or 1
    }

    @DisplayName("'getOne' employee (by id) test")
    @Test
    public void givenEmployeeId_whenGetOne_thenReturnEmployee() throws ResourceNotFoundException {
        // given
        EmployeeRequestDto miaEmployeeDto955 = new EmployeeRequestDto(955l, "Mia", HOURLY);
        long idOfNewEmp = employeeDao.addOne(miaEmployeeDto955);

        // when
        AbstractEmployee actualEmployee = employeeDao.getOne(idOfNewEmp);

        // then
        assertThat(actualEmployee.getName()).isNotBlank();
    }

    @DisplayName("'getOne' employee (by id) not found throws test")
    @Test
    public void givenEmployeeIdNotExists_whenGetOne_thenThrows() {
        // given
        AbstractEmployee actualEmployee = null;
        long badId = 3293092l;
        // when
        try {
            actualEmployee = employeeDao.getOne(badId);
        } catch (ResourceNotFoundException e) {
            assertThat(e).hasMessage("Employee with id '" + badId + "' not found.");
        }
        // then
        assertThat(actualEmployee).isNull();
    }

    @DisplayName("'addOne' employee test")
    @Test
    public void givenEmployeeDto_whenAddOne_thenSaveEmployee() throws ResourceNotFoundException {
        // given
        EmployeeRequestDto carlEmployeeDto6234 = new EmployeeRequestDto(6234l, "carl", HOURLY);            // when
        long addOneId = employeeDao.addOne(carlEmployeeDto6234);

        AbstractEmployee employee = employeeDao.getOne(addOneId);

        // then
        assertThat(employee.getName()).isEqualTo("carl");
    }

    @DisplayName("'updateOne' employee by dto test")
    @Test
    public void givenEmployeeAndMatchingDto_whenUpdateOne_thenUpdateEmployee() throws ResourceNotFoundException {
        // given
        EmployeeRequestDto moeEmployeeDto3334 = new EmployeeRequestDto(3334l, "moe", HOURLY);

        // when
        long moeId = employeeDao.addOne(moeEmployeeDto3334);
        EmployeeRequestDto newEmployeeDto = new EmployeeRequestDto(moeId, "carl", MANAGER);
        employeeDao.updateOne(newEmployeeDto);
        AbstractEmployee updatedEmployee = employeeDao.getOne(moeId);

        // then
        assertThat(updatedEmployee.getRole()).isEqualTo(MANAGER);
    }
}