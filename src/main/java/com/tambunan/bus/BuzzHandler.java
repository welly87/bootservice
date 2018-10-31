package com.tambunan.bus;

import com.google.common.eventbus.Subscribe;

public abstract class BuzzHandler<T extends BuzzMessage> {

    @Subscribe
    public void handle(BuzzEnvelop envelop) {
        // TODO how to send just to one handler, currently sent to all message handler
        try {
            handle((T) envelop.getMessage(), envelop.getContext());
        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }
    }

    public abstract void handle(T message, BuzzContext context);
}
