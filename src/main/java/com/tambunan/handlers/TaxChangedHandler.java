package com.tambunan.handlers;

import com.google.common.eventbus.Subscribe;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzMessage;
import com.tambunan.messages.TaxChanged;

public class TaxChangedHandler implements BuzzHandler<TaxChanged> {
    @Override
    @Subscribe
    public void handle(TaxChanged message) {
        System.out.println("Handling TaxChanged message ");
    }
}
