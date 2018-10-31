package com.tambunan.handlers;

import com.tambunan.bus.BuzzContext;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzSubscribe;
import com.tambunan.messages.CalculatePayroll;
import com.tambunan.messages.CalculatePayrollResult;
import com.tambunan.messages.CreateUser;
import com.tambunan.messages.PaymentReceived;

@BuzzSubscribe(topic = "com.tambunan.messages.CalculatePayroll")
public class CalculatePayrollHandler extends BuzzHandler<CalculatePayroll> {

    @Override
    public void handle(CalculatePayroll message, BuzzContext context) {
        System.out.println("CalculatePayroll -> " + message.getEmployeeId());

        context.reply(new CalculatePayrollResult());

        context.send(new CreateUser("id", "name"));

        context.publish(new PaymentReceived());
    }
}
