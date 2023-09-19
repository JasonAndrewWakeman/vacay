package com.jw.vacationbalance.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode()
@ToString
@Data
public class VacationDto {

    /**
     *
     * @param employeeId the id of the employee for which the vacation is issued
     * @param vacationDays (float) the amount of vacation days being issued
     */
    public VacationDto(long employeeId, float vacationDays) {
        this.employeeId = employeeId;
        this.vacationDays = vacationDays;
    }

    @Getter
    private long employeeId;

    @Getter
    private float vacationDays;


}
