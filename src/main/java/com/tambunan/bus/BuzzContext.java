package com.tambunan.bus;

public interface BuzzContext {
    BuzzHeader header();
    void reply(BuzzMessage message);
    void send(BuzzMessage message);
    void publish(BuzzMessage message);
}
