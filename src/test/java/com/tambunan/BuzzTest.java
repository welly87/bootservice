package com.tambunan;

import com.tambunan.bus.Bus;
import com.tambunan.handlers.CalculatePayrollHandler;
import com.tambunan.handlers.EmployeeAttendHandler;
import com.tambunan.handlers.EmployeeCreatedHandler;
import com.tambunan.handlers.PaymentReceivedHandler;
import com.tambunan.messages.CalculatePayroll;
import com.tambunan.messages.PaymentReceived;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuzzTest {
    @Autowired
    Bus bus;

    @Test
    public void should_register_all_interesting_event() throws Exception {

        // TODO should be able to subscribe automatically

//        bus.subscribe("com.tambunan.messages.PaymentReceived", new PaymentReceivedHandler());
//
//        bus.subscribe("com.tambunan.messages.EmployeeAttend", new EmployeeAttendHandler());
//
//        bus.subscribe("com.tambunan.messages.EmployeeCreated", new EmployeeCreatedHandler());
//
//        bus.handleCommand("com.tambunan.messages.CalculatePayroll", new CalculatePayrollHandler());

        bus.start();

        bus.send("com.tambunan.messages.CalculatePayroll", new CalculatePayroll("GuidEmployeeId"));

        bus.publish(new PaymentReceived());

        Thread.sleep(100000);
    }
}
