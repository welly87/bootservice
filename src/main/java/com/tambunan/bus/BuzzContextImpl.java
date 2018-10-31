package com.tambunan.bus;

public class BuzzContextImpl implements BuzzContext {

    private BuzzHeader header;
    private Bus bus;

    public BuzzContextImpl(BuzzHeader header, Bus bus) {
        this.header = header;
        this.bus = bus;
    }

    @Override
    public BuzzHeader header() {
        return header;
    }

    @Override
    public void reply(BuzzMessage message) {
        bus.send(header.replyTo(), message);
    }

    @Override
    public void send(BuzzCommand message) {
        bus.send(message.getClass().getPackage().getName(), message);
    }

    @Override
    public void publish(BuzzEvent message) {
        bus.publish(message);
    }
}
