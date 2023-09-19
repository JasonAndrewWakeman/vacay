package com.jw.vacationbalance.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode()
@ToString
public class VacationStatement {

    /**
     *
     * @param id the pseudo primary key identifier of a vacation issuance statement
     * @param employeeId the id of the employee for which the vacation is issued
     * @param vacationDays (float) the amount of vacation days being issued
     */
    public VacationStatement(long id, long employeeId, float vacationDays) {
        this.id = id;
        this.employeeId = employeeId;
        this.vacationDays = vacationDays;
    }

    @Getter
    private final long id;

    @Getter
    private final long employeeId;

    @Getter
    private final float vacationDays;
}
