package com.tambunan.handlers;

import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;
import com.tambunan.messages.TaxChanged;

public class TaxChangedHandler implements BuzzHandler<BuzzMessage> {
    @Override
    public void handle(BuzzMessage message) {
        TaxChanged msg = (TaxChanged)message;

        System.out.println("Handling TaxChanged message ");
    }
}
