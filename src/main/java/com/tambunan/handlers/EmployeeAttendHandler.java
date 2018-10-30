package com.tambunan.handlers;

import com.google.common.eventbus.Subscribe;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;
import com.tambunan.messages.EmployeeAttend;
import com.tambunan.messages.EmployeeCreated;

public class EmployeeAttendHandler implements BuzzHandler<EmployeeAttend> {

    @Override
    @Subscribe
    public void handle(EmployeeAttend message) {
        System.out.println("Handling EmployeeAttend message");
    }
}
