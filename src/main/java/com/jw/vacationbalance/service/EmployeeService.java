package com.jw.vacationbalance.service;

import com.jw.vacationbalance.dto.EmployeeRequestDto;
import com.jw.vacationbalance.entity.employee.AbstractEmployee;
import com.jw.vacationbalance.dao.EmployeeDao;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao dao;

    /**
     * Get all employees.
     * 
     * @return List<AbstractEmployee> employees
     */
    public List<AbstractEmployee> getAll() {
        return dao.getAll();
    }


    /**
     * Get one employee by id.
     * 
     * @param id the id of the employee to fetch
     * @return AbstractEmployee
     * @throws ResourceNotFoundException
     */

    public AbstractEmployee getOne(long id) throws ResourceNotFoundException {
        return dao.getOne(id);
    }


    /**
     * Add new employee.
     *
     * @param employeeDto
     */
    public long addOne(EmployeeRequestDto employeeDto) {
        return dao.addOne(employeeDto);
    }


    /**
     * Update one employee.
     * 
     * @param employeeDto A data transfer object with role and/or name fields to update.
     */
    public void updateOne(EmployeeRequestDto employeeDto) throws ResourceNotFoundException {
        dao.updateOne(employeeDto);
    }


    /**
     * Delete employee by id.
     * 
     * @param id
     */
    public void deleteOne(long id) throws ResourceNotFoundException {
        dao.deleteOne(id);
    }
}
