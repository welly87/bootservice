package com.tambunan;

import com.tambunan.bus.MessageListeners;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    private static void startListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageListeners listener = new MessageListeners();
                try {
                    listener.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}