package com.tambunan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.tambunan.domain.Employee;
import com.tambunan.services.EmployeeService;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTest {

    @Autowired
    private EmployeeService service;

    @Test
    public void should_not_have_acceess_to_door_after_resign() {
        long userId = 1;

        service.newEmployee(userId, "name");

        service.employeeJoinCompany(userId, new Date());

        service.resign(userId, new Date());

        assertFalse(service.enterDoor(userId));
    }

    @Test
    public void should_not_have_access_to_door_after_resign() {

        Employee employee = new Employee(1L, "Welly Tambunan");

        employee.join(new Date());

        assertTrue(employee.hasAccessDoor());

        employee.resign();

        assertFalse(employee.hasAccessDoor());
    }

    @Test
    public void Should_receive_bonus_100jt_if_resign_after_10_years() {
        
    }
}
