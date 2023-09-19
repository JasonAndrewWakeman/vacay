package com.jw.vacationbalance.service;

import com.jw.vacationbalance.dao.EmployeeDao;
import com.jw.vacationbalance.dto.VacationDto;
import com.jw.vacationbalance.entity.employee.AbstractEmployee;
import com.jw.vacationbalance.exception.BadRequestException;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationService {

    @Autowired
    private EmployeeDao dao;

    /**
     * Process vacation statement
     *
     * @see "BUSINESS LOGIC FROM REQUIREMENT DOCUMENTATION <name><location>"
     * An employee cannot take more vacation than is available."
     * 
     * @return the pseudo primary key id of the created vacation statement
     */
    public long processVacation(VacationDto vacationDto)
            throws BadRequestException, ResourceNotFoundException {
        AbstractEmployee e = dao.getOne(vacationDto.getEmployeeId());
        float newVacationBalance = computeDecreasedVacation(e.getVacationBalance(), vacationDto);
        e.setVacationBalance(newVacationBalance);
        dao.updateOne(e);
        return dao.addVacationStatement(e.getId(), vacationDto.getVacationDays());
    }

    float computeDecreasedVacation(float vacationBalance, VacationDto vacationDto) throws BadRequestException {
        float vacationDays = vacationDto.getVacationDays();
        if (vacationDays > vacationBalance || vacationDays < 0) {
            throw new BadRequestException("Vacation days must be between 0 and the amount currently available (" + vacationBalance + ")");
        }
        return vacationBalance - vacationDays;
    }
}
