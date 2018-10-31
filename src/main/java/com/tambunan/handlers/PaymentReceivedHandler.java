package com.tambunan.handlers;

import com.tambunan.bus.BuzzContext;
import com.tambunan.bus.BuzzHandler;
import com.tambunan.bus.BuzzHeader;
import com.tambunan.bus.BuzzSubscribe;
import com.tambunan.messages.PaymentReceived;


@BuzzSubscribe(topic = "com.tambunan.messages.PaymentReceived")
public class PaymentReceivedHandler extends BuzzHandler<PaymentReceived> {

    @Override
    public void handle(PaymentReceived message, BuzzContext context) {
        System.out.println("Handling PaymentReceived message ");

        BuzzHeader header = context.header();

        System.out.println(header.getMessageType());
    }
}
