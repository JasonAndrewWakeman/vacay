package com.jw.vacationbalance.entity.employee;


import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 *  enum for the types of employees with vacationAllotment getter
 */
public enum Role {

    /**
     *  @see "BUSINESS LOGIC FROM REQUIREMENT DOCUMENTATION <name>, <location>"
     *  "Hourly employees accumulate 10 vacation days during the work year."
     *  "Salaried employees accumulate 15 vacation days during the work year."
     *  "Manager employees accumulate 30 vacation days during the work year."
     *
     *  should consider making allotment values configurable at deployment.
     */
    HOURLY("Hourly", 10f),
    SALARIED("Salaried", 15f),
    MANAGER("Manager", 30f);


    /**
     * The role of the employee
     *
     * @param type one of "HOURLY", "SALARIED", or "MANAGER"
     * @param vacationAllotment the number of days of vacation an employee of this type accrues in 1 year
     */
    Role(String type, float vacationAllotment) {
        this.type = type;
        this.vacationAllotment = vacationAllotment;
    }

    @Getter
    private final String type;

    @Getter
    private float vacationAllotment;


}
