package com.tambunan;

import com.tambunan.bus.BootBuzz;
import com.tambunan.bus.Bus;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.messages.*;
import org.junit.Test;

public class BuzzTest {
    @Test
    public void should_register_all_interesting_event() {
        Bus bus = new BootBuzz();

        bus.subscribe("com.payroll.TaxChanged", new BuzzHandler<TaxChanged>() {
            @Override
            public void handle(TaxChanged message) {

                System.out.println("Handling TaxChanged message");
            }
        });


        bus.subscribe("com.attendance.EmployeeAttend", new BuzzHandler<EmployeeAttend>() {
            @Override
            public void handle(EmployeeAttend message) {

                System.out.println("Handling EmployeeAttend message");
            }
        });


        bus.subscribe("com.hr.EmployeeCreated", new BuzzHandler<EmployeeCreated>() {
            @Override
            public void handle(EmployeeCreated message) {
                System.out.println("Handling EmployeeCreated message");
            }
        });

        bus.handleCommand("com.hr.CreateSomething", new BuzzHandler<CreateAnalytics>() {
            @Override
            public void handle(CreateAnalytics message) {

            }
        });

        bus.start();

        bus.send("com.payroll.CalculatePayroll", new CalculatePayroll("employeeId"));

        bus.publish(new PaymentReceived());
    }
}
