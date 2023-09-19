package com.jw.vacationbalance.entity.employee;

/**
 * A salaried 'type' of employee
 */
public class SalariedEmployee extends AbstractEmployee {

    /**
     * An employee with salaried role
     *
     * @param id the pseudo primary key identifier of an employee
     * @param name the name of the employee
     */
    public SalariedEmployee(long id, String name) {
        super(id, name, Role.SALARIED);
    }
}
