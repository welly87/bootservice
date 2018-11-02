package com.tambunan.handlers;

import com.google.common.eventbus.Subscribe;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;
import com.tambunan.messages.EmployeeCreated;

public class EmployeeCreatedHandler implements BuzzHandler<EmployeeCreated> {
    @Subscribe
    public void handle(EmployeeCreated message) {
        System.out.println("Handling EmployeeCreated message");
    }
}
