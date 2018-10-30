package com.tambunan.handlers;

import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;
import com.tambunan.messages.EmployeeAttend;

public class EmployeeAttendHandler implements BuzzHandler<BuzzMessage> {
    @Override
    public void handle(BuzzMessage message) {
        EmployeeAttend msg = (EmployeeAttend)message;

        System.out.println("Handling EmployeeAttend message");
    }
}
