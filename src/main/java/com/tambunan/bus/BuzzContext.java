package com.tambunan.bus;

public interface BuzzContext {
    BuzzHeader header();
    void reply(BuzzMessage message);
    void send(BuzzCommand message);
    void publish(BuzzEvent message);
}
