package com.jw.vacationbalance.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode()
@ToString
public class WorkDto {

    /**
     *
     * @param employeeId the id of the employee for which work statement is issued
     * @param workedDays (integer) the number of days worked
     */
    public WorkDto(long employeeId, int workedDays) {
        this.employeeId = employeeId;
        this.workedDays = workedDays;
    }

    @Getter
    private long employeeId;

    @Getter
    private int workedDays;


}
