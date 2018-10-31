package com.tambunan.handlers;

import com.tambunan.bus.BuzzContext;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.messages.EmployeeCreated;

public class EmployeeCreatedHandler extends BuzzHandler<EmployeeCreated> {

    public void handle(EmployeeCreated message, BuzzContext context) {
        System.out.println("Handling EmployeeCreated message");
    }
}
