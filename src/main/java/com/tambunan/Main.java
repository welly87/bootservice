package com.tambunan;

import com.tambunan.bus.MessageListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Main {
    @Autowired
    private MessageListeners listener;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void startListener() {
        new Thread(() -> {
            try {
                listener.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}