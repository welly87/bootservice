package com.tambunan.controllers;

import com.tambunan.bus.BootBuzz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tambunan.messages.CreateUser;
import com.tambunan.services.EmployeeService;

@RestController
public class EmployeeController {

    @Autowired
    private BootBuzz _buzz;

    @Autowired
    private EmployeeService _service;

    @RequestMapping("/")
    String home() {

        _buzz.send("welly", new CreateUser("uuid", "Welly"));

        return "Hello";
    }
}
