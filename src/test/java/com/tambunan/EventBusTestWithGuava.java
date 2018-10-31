package com.tambunan;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.tambunan.messages.CalculatePayroll;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


class CalculatePayroll2Handler {
    @Subscribe
    public void handle(CalculatePayroll msg) {
        System.out.println(msg.getEmployeeId() + " payroll 2");
    }
}

class CalculatePayroll3Handler {
    @Subscribe
    public void handle(CalculatePayroll msg) {
        System.out.println(msg.getEmployeeId() + " payroll 3");
    }
}


public class EventBusTestWithGuava {
    @Test
    public void should_handle_message_generically() {
        EventBus bus = new EventBus();

        bus.register(new CalculatePayroll2Handler());
        bus.register(new CalculatePayroll3Handler());

        bus.post(new CalculatePayroll("employeeid"));
    }

    @Test
    public void should_create_generic_type_object() throws Exception {
//        assertEquals("", Class.forName("com.tambunan.messages.CalculatePayroll"));

        Gson gson = new Gson();

        String body = gson.toJson(new CalculatePayroll("employeeid"));

        Object obj = gson.fromJson(body, Class.forName("com.tambunan.messages.CalculatePayroll"));

        assertNotNull(((CalculatePayroll)obj).getEmployeeId());

        System.out.println(((CalculatePayroll)obj).getEmployeeId());
    }
}
