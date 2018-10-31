package com.tambunan.bus;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class BuzzHandlerRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(BuzzHandlerRunnable.class);

    private KafkaConsumer<String, String> kafkaConsumer;
    private HashMap<String, BuzzHandler<?>> handlerMaps;
    private Gson gson = new Gson();

    public BuzzHandlerRunnable(Properties props, HashMap<String, BuzzHandler<?>> handlerMaps) {
        this.kafkaConsumer = new KafkaConsumer<>(props);
        this.handlerMaps = handlerMaps;

        // TODO add rebalancer listener to handle partition change event from kafka
        kafkaConsumer.subscribe(this.handlerMaps.keySet());
    }

    @Override
    public void run() {
        log.debug("[BuzzHandler] run");
        while (true) {
            try {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(5000));

                for (ConsumerRecord<String, String> record : records) {

                    String messageType = new String(Arrays.stream(record.headers().toArray())
                            .filter(x -> x.key().equals("message-type")).findFirst().get().value());

                    BuzzMessage message = (BuzzMessage) gson.fromJson(record.value(), Class.forName(messageType));

                    System.out.println(messageType);

                    MessageListeners.bus.post(message);
                }

                kafkaConsumer.commitSync();
            } catch (Exception e) {
                log.error("Error on keep-alive consumer : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
