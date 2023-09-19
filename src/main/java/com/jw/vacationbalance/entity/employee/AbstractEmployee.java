package com.jw.vacationbalance.entity.employee;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode()
@ToString
public abstract class AbstractEmployee {

    /**
     *  @see "BUSINESS LOGIC FROM REQUIREMENT DOCUMENTATION <name>, <location>"
     *  the maximum number of workdays in 1 year wrt vacation accrual purposes
     */
    private final static int WORKDAYS_IN_YEAR = 260;

    /**
     *
     * @param id the pseudo primary key identifier of an employee
     * @param name the name of the employee
     * @param role the role of the employee
     */
    public AbstractEmployee(long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.annualVacationAllotmentDays = role.getVacationAllotment();
        this.vacationBalance = 0;
        this.vacationAccrualRate = annualVacationAllotmentDays/WORKDAYS_IN_YEAR;
    }

    @Getter
    private final Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Role role;

    private final float annualVacationAllotmentDays;

    @Getter
    private final float vacationAccrualRate;

    @Getter
    @Setter
    private float vacationBalance;
}
