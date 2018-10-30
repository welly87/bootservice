package com.tambunan.handlers;

import com.google.common.eventbus.Subscribe;
import com.tambunan.messages.CreateUser;
import com.tambunan.bus.BuzzHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateEmployeeHandler implements BuzzHandler<CreateUser> {

    @Override
    @Subscribe
    public void handle(CreateUser message) {
        System.out.println(message.getId());
        System.out.println(message.getName());
    }
}
