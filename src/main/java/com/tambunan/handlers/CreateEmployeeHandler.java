package com.tambunan.handlers;

import com.tambunan.bus.BuzzContext;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.messages.CreateUser;
import org.springframework.stereotype.Component;

@Component
public class CreateEmployeeHandler extends BuzzHandler<CreateUser> {

    @Override
    public void handle(CreateUser message, BuzzContext context) {
        System.out.println(message.getId());
        System.out.println(message.getName());
    }
}
