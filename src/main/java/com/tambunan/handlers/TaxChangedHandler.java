package com.tambunan.handlers;

import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;

public class TaxChangedHandler implements BuzzHandler<BuzzMessage> {
    @Override
    public void handle(BuzzMessage message) {
        System.out.println("Handling TaxChangedHandler message");
    }
}
