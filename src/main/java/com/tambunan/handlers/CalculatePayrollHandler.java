package com.tambunan.handlers;

import com.google.common.eventbus.Subscribe;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzSubscribe;
import com.tambunan.messages.CalculatePayroll;

@BuzzSubscribe(topic = "com.tambunan.messages.CalculatePayroll")
public class CalculatePayrollHandler implements BuzzHandler<CalculatePayroll> {

    @Override
    @Subscribe
    public void handle(CalculatePayroll message) {
        System.out.println("CalculatePayroll -> " + message.getEmployeeId());
    }
}
