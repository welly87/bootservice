package com.tambunan.handlers;

import com.google.common.eventbus.Subscribe;
import com.tambunan.bus.BuzzContext;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;
import com.tambunan.messages.EmployeeAttend;
import com.tambunan.messages.EmployeeCreated;

public class EmployeeAttendHandler extends BuzzHandler<EmployeeAttend> {

    @Override
    public void handle(EmployeeAttend message, BuzzContext context) {
        System.out.println("Handling EmployeeAttend message");
    }
}
