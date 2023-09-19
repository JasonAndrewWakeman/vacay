package com.jw.vacationbalance.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode()
@ToString
public class WorkStatement {
    /**
     *
     * @param id the pseudo primary key identifier of the statement of work
     * @param employeeId the id of the employee for which work statement is issued
     * @param workedDays (integer) the number of days worked
     */
    public WorkStatement(long id, long employeeId, int workedDays) {
        this.id = id;
        this.employeeId = employeeId;
        this.workedDays = workedDays;
    }

    @Getter
    private final long id;

    @Getter
    private final long employeeId;

    @Getter
    private final int workedDays;
}
