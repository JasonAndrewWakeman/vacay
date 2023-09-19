package com.jw.vacationbalance.entity.employee;

/**
 * A manager 'type' of employee
 */
public class ManagerEmployee extends AbstractEmployee {

    /**
     * An employee with manager role
     *
     * @param id the pseudo primary key identifier of an employee
     * @param name the name of the employee
     */
    public ManagerEmployee(long id, String name) {
        super(id, name, Role.MANAGER);
    }
}
