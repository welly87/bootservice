package com.tambunan;

import org.junit.Test;
import tambunan.domain.Employee;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeTest {


    @Test
    public void should_not_have_access_to_door_after_resign() {



        Employee employee = new Employee();

        employee.join(new Date());

        assertTrue(employee.hasAccessDoor());

        employee.receiveSalary(10000);

        employee.resign();

        assertFalse(employee.hasAccessDoor());
    }

    @Test
    public void Should_receive_bonus_100jt_if_resign_after_10_years() {

    }
}
