package com.jw.vacationbalance.entity.employee;

/**
 * An hourly 'type' of employee
 */
public class HourlyEmployee extends AbstractEmployee {

    /**
     * An employee with hourly role
     *
     * @param id the pseudo primary key identifier of an employee
     * @param name the name of the employee
     */
    public HourlyEmployee(long id, String name) {
        super(id, name, Role.HOURLY);
    }
}

