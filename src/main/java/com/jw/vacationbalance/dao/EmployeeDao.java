package com.jw.vacationbalance.dao;

import com.jw.vacationbalance.dto.EmployeeRequestDto;
import com.jw.vacationbalance.entity.WorkStatement;
import com.jw.vacationbalance.entity.employee.AbstractEmployee;
import com.jw.vacationbalance.entity.employee.HourlyEmployee;
import com.jw.vacationbalance.entity.employee.ManagerEmployee;
import com.jw.vacationbalance.entity.employee.SalariedEmployee;
import com.jw.vacationbalance.entity.VacationStatement;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@Component
public class EmployeeDao {
    private static final String ID_NOT_FOUND_MSG_1 = "Employee with id '";
    private static final String ID_NOT_FOUND_MSG_2 = "' not found.";

    @Autowired
    private Uuid uuid;

    private static final List<AbstractEmployee> employeeList = new CopyOnWriteArrayList<>();

    /**
     *
     * @return
     */
    public List<AbstractEmployee> getAll() {
        return employeeList;
    }


    /**
     * Find one employee by id.
     *
     * @param id
     * @return
     */
    public AbstractEmployee getOne(Long id) throws ResourceNotFoundException {
        return employeeList.stream()
                .filter(e -> e.getId() == id)
                .findAny()
                .orElseThrow(() -> {
                    String msg = getIdNotFoundMsg(id);
                    return new ResourceNotFoundException(msg);
                });
    }

    /**
     * Add a new employee.
     *
     * @param employeeDto
     */
    public long addOne(EmployeeRequestDto employeeDto) {
        long newId = uuid.nextId();
        switch (employeeDto.getRole()) {
            case HOURLY:
                employeeList.add(new HourlyEmployee(newId, employeeDto.getName()));
                break;
            case SALARIED:
                employeeList.add(new SalariedEmployee(newId, employeeDto.getName()));
                break;
            case MANAGER:
                employeeList.add(new ManagerEmployee(newId, employeeDto.getName()));
                break;
            default:
                Logger.getAnonymousLogger().warning("employeeDto contained invalid role" + employeeDto.toString());
                break;
        }
        return newId;
    }

    /**
     *
     * @param employee the employee being updated
     * @throws ResourceNotFoundException if no employee with this id is found
     */
    public void updateOne(AbstractEmployee employee) throws ResourceNotFoundException {
        AbstractEmployee existing = getOne(employee.getId());
        // SHOULD BE SINGLE ATOMIC DB TRANSACTION
        employeeList.remove(existing);
        employeeList.add(employee);
        return;
    }

    /**
     *
     * @param employeeDto data transfer object representing the employee's updates
     * @throws ResourceNotFoundException
     */
    public void updateOne(EmployeeRequestDto employeeDto) throws ResourceNotFoundException {
        AbstractEmployee existing = getOne(employeeDto.getId());
        // SHOULD BE SINGLE ATOMIC DB TRANSACTION
        employeeList.remove(existing);
        existing.setName(employeeDto.getName());
        existing.setRole(employeeDto.getRole());
        employeeList.add(existing);
        return;
    }


    /**
     * Delete one employee by id.
     *
     * @param id
     */
    public void deleteOne(Long id) throws ResourceNotFoundException {
        AbstractEmployee existing = getOne(id);
        employeeList.remove(existing);
        return;
    }

    /**
     *
     * @param employeeId the id of the employee issued vacation
     * @param vacationDays (float) the amount of vacation days issued
     * @return the pseudo primary key id of the created vacation staement
     */
    public long addVacationStatement(long employeeId, float vacationDays) {
        return new VacationStatement(uuid.nextId(), employeeId, vacationDays).getId();
    }

    /**
     *
     * @param employeeId the id of the employee issued vacation
     * @param workedDays (int) the number of days worked
     * @return the pseudo primary key id of the created work staement
     */
    public long addWorkStatement(long employeeId, int workedDays) {
        return new WorkStatement(uuid.nextId(), employeeId, workedDays).getId();
    }

    /*****************************************************************************************
     *                                        MESSAGE HELPERS
     *****************************************************************************************
     /**


     /**
     * Interpolate message for id not found.
     *
     * @return message for the exception
     */
    private static String getIdNotFoundMsg(Long id) {
        return ID_NOT_FOUND_MSG_1 + id + ID_NOT_FOUND_MSG_2;
    }



    /*****************************************************************************************
     *                                       MOCK DB SEEDS
     *****************************************************************************************
    /**
     * Autowired to update the Dao class's employee list with seed data
     *
     * @param hourlySeeds from application properties
     * @param salariedSeeds from application properties
     * @param managerSeeds from application properties
     */
    @Autowired
    private void seedEmployeeList(
            @Value("${seeds.employees.hourly}") final String hourlySeeds,
            @Value("${seeds.employees.salaried}") final String salariedSeeds,
            @Value("${seeds.employees.manager}") final String managerSeeds) {
        for (String name : Arrays.asList(hourlySeeds.split(","))) {
            employeeList.add(new HourlyEmployee(uuid.nextId(), name));
        }
        for (String name : Arrays.asList(salariedSeeds.split(","))) {
            employeeList.add(new SalariedEmployee(uuid.nextId(), name));
        }
        for (String name : Arrays.asList(managerSeeds.split(","))) {
            employeeList.add(new ManagerEmployee(uuid.nextId(), name));
        }
        return;
    }
}