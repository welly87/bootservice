package com.tambunan.bus;

public interface Bus {

    void subscribe(String eventName, BuzzHandler<BuzzMessage> handler);

    void handleCommand(String commandName, BuzzHandler<BuzzMessage> handler);

    void send(String destination, BuzzMessage cmd);

    void publish(BuzzMessage event);

    void start();
}
