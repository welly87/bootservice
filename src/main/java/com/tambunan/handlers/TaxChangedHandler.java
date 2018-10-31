package com.tambunan.handlers;

import com.tambunan.bus.BuzzContext;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzHeader;
import com.tambunan.messages.TaxChanged;

public class TaxChangedHandler extends BuzzHandler<TaxChanged> {

    @Override
    public void handle(TaxChanged message, BuzzContext context) {
        System.out.println("Handling TaxChanged message ");

        BuzzHeader header = context.header();


    }
}
