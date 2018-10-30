package com.tambunan;

import com.google.gson.Gson;
import com.tambunan.bus.BootBuzz;
import com.tambunan.bus.Bus;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;
import com.tambunan.messages.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuzzTest {
    @Autowired
    Bus bus;

    @Test
    public void should_register_all_interesting_event() throws Exception {

        bus.subscribe("com.tambunan.messages.TaxChanged", new BuzzHandler<BuzzMessage>() {
            @Override
            public void handle(BuzzMessage message) {
                System.out.println("Handling TaxChanged message");
            }
        });


        bus.subscribe("com.tambunan.messages.EmployeeAttend", new BuzzHandler<BuzzMessage>() {
            @Override
            public void handle(BuzzMessage message) {

                System.out.println("Handling EmployeeAttend message");
            }
        });


        bus.subscribe("com.tambunan.messages.EmployeeCreated", new BuzzHandler<BuzzMessage>() {
            @Override
            public void handle(BuzzMessage message) {
                System.out.println("Handling EmployeeCreated message");
            }
        });

        bus.handleCommand("com.tambunan.messages.CalculatePayroll", new BuzzHandler<BuzzMessage>() {
            @Override
            public void handle(BuzzMessage message) {
                CalculatePayroll msg = (CalculatePayroll)message;

                System.out.println(msg.getEmployeeId());
            }
        });

        bus.start();

        bus.send("com.tambunan.messages.CalculatePayroll", new CalculatePayroll("employeeId"));

        bus.publish(new PaymentReceived());

        Thread.sleep(100000);
    }

//    @Test
    public void should_create_generic_type_object() throws Exception {
//        assertEquals("", Class.forName("com.tambunan.messages.CalculatePayroll"));

        Gson gson = new Gson();

        String body = gson.toJson(new CalculatePayroll("employeeid"));

        Object obj = gson.fromJson(body, Class.forName("com.tambunan.messages.CalculatePayroll"));

        assertNotNull(((CalculatePayroll)obj).getEmployeeId());

        System.out.println(((CalculatePayroll)obj).getEmployeeId());
    }
}
