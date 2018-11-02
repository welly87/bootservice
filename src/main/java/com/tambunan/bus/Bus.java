package com.tambunan.bus;

public interface Bus {

    <T extends BuzzMessage> void subscribe(String eventName, BuzzHandler<T> handler);

    <T extends BuzzMessage> void handleCommand(String commandName, BuzzHandler<T> handler);

    void send(String destination, BuzzMessage cmd);

    void publish(BuzzMessage event);

    void start();
}
