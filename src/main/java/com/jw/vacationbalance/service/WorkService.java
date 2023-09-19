package com.jw.vacationbalance.service;

import com.jw.vacationbalance.dao.EmployeeDao;
import com.jw.vacationbalance.dto.WorkDto;
import com.jw.vacationbalance.entity.employee.AbstractEmployee;
import com.jw.vacationbalance.exception.BadRequestException;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkService {

    @Autowired
    private EmployeeDao dao;

    // should probably be configurable
    private static final int MAX_WORK_DAYS = 260;


    /**
     /**
     * Process work statement
     *
     * @see "BUSINESS LOGIC FROM REQUIREMENT DOCUMENTATION <name><location>"z
     * "An employee cannot work more than the number of workdays in work year (260)"
     *
     * @param workDto
     *
     * @return the pseudo primary key id of the created work statement
     *
     * @throws ResourceNotFoundException
     * @throws BadRequestException
     */
    public long processWork(WorkDto workDto) throws BadRequestException, ResourceNotFoundException {
        AbstractEmployee e = dao.getOne(workDto.getEmployeeId());
        e.setVacationBalance(computeIncreasedVacation(e, workDto.getWorkedDays()));
        dao.updateOne(e);
        // write workStatement to db should be part of single atomic db transaction with update employee
        return dao.addWorkStatement(e.getId(), workDto.getWorkedDays());
    }

    /**
     * @see "BUSINESS LOGIC FROM REQUIREMENT DOCUMENTATION <name><location>"z
     * "An employee cannot work more than the number of workdays in work year (260)"
     *
     * @param e
     * @param workedDays
     * @return
     * @throws BadRequestException
     */
     float computeIncreasedVacation(AbstractEmployee e, int workedDays) throws BadRequestException {
        if (workedDays > MAX_WORK_DAYS || workedDays < 0) {
            throw new BadRequestException("Days worked must be between 0 and 260");
        }
        return e.getVacationBalance() + e.getVacationAccrualRate() * workedDays;
    }
}
