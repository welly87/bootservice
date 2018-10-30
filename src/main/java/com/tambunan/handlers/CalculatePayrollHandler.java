package com.tambunan.handlers;

import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;
import com.tambunan.messages.CalculatePayroll;

public class CalculatePayrollHandler implements BuzzHandler<BuzzMessage> {
    @Override
    public void handle(BuzzMessage message) {
        CalculatePayroll msg = (CalculatePayroll)message;

        System.out.println(msg.getEmployeeId());
    }
}
