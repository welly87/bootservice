package com.tambunan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tambunan.domain.Employee;
import com.tambunan.repositories.EmployeeRepository;

import java.util.Date;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public boolean enterDoor(long userId) {
        Employee employee = repository.findById(userId).get();

        return employee.hasAccessDoor();
    }

    public void newEmployee(long userId, String name) {
        Employee employee = new Employee(userId, name);
        repository.save(employee);
    }

    public void employeeJoinCompany(long userId, Date date) {
        Employee employee = repository.findById(userId).get();
        employee.join(date);
        repository.save(employee);
    }

    public void resign(long userId, Date date) {
        Employee employee = repository.findById(userId).get();
        employee.resign();
        repository.save(employee);
    }
}
