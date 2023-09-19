package com.jw.vacationbalance.dao;

import com.jw.vacationbalance.exception.BadRequestException;
import com.jw.vacationbalance.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UuidTest {

    @DisplayName("'nextId' should return a long greater than zero")
    @Test
    public void givenUuid_whenNextId_thenGreaterThan0() throws BadRequestException, ResourceNotFoundException {
        // given
        Uuid uuid = new Uuid();

        //when
        long id = uuid.nextId();

        //then
        assertThat(id).isGreaterThan(0l); //Note since it is a singleton other tests in this context could have incremented the value
    }


    @DisplayName("'nextId' should return a different bigger long the second time")
    @Test
    public void givenUuid_whenNextId_thenBigger() throws BadRequestException, ResourceNotFoundException {
        // given
        Uuid uuid = new Uuid();

        //when
        long id = uuid.nextId();
        long otherId = uuid.nextId();

        //then
        assertThat(otherId).isGreaterThan(id);
    }
}
