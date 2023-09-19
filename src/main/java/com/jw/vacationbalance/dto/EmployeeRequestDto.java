package com.jw.vacationbalance.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import com.jw.vacationbalance.entity.employee.Role;

@Data
@EqualsAndHashCode()
@ToString
public class EmployeeRequestDto {

    /**
     *
     * @param id the id of the employee to update (ignored if creating from dto).
     * @param name The employees full name
     * @param role The role of the employee
     */
    public EmployeeRequestDto(long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    @Getter
    private final long id;

    @Getter
    private final String name;

    @Getter
    @NonNull
    private final Role role;
}