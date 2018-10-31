package com.tambunan.bus;

public class BuzzEnvelop {
    private BuzzMessage message;
    private BuzzContext context;

    public BuzzEnvelop(BuzzMessage message, BuzzContext context) {
        this.message = message;
        this.context = context;
    }

    public BuzzMessage getMessage() {
        return message;
    }

    public BuzzContext getContext() {
        return context;
    }
}
