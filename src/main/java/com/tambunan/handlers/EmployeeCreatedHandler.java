package com.tambunan.handlers;

import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;
import com.tambunan.messages.EmployeeCreated;

public class EmployeeCreatedHandler implements BuzzHandler<BuzzMessage> {
    @Override
    public void handle(BuzzMessage message) {
        EmployeeCreated msg = (EmployeeCreated)message;

        System.out.println("Handling EmployeeCreated message");
    }
}
