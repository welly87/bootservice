package com.tambunan.messages;

import com.tambunan.bus.BuzzCommand;

public class CreateUser implements BuzzCommand {
    private String id;

    private String name;

    public CreateUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
