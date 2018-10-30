package com.tambunan.bus;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;

@Component
public class MessageListeners {

    private static final byte MAX_CONCURRENCY = 10;

    private static final byte MIN_CONCURRENCY = 1;

    @Value("${bootservice.kafka.servers}")
    private String servers;

    @Value("${bootservice.kafka.schemaregistry.url}")
    private String schemaUrl;

    private EventBus bus = new EventBus();

    // TODO .. we need to change this to Map of <string, List>
    private HashMap<String, BuzzHandler<?>> _handlerMaps = new HashMap<>();

    @Value("${bootservice.kafka.concurrency:1}")
    private byte concurrencyFactor;

    private ExecutorService executor;

    public void start() throws Exception {
        if (_handlerMaps.isEmpty()) {
            throw new BuzzException("You need to subscribe to topics, please register message handler first");
        }

        if (concurrencyFactor < MIN_CONCURRENCY || concurrencyFactor > MAX_CONCURRENCY) {
            throw new BuzzException("Concurrency must between " + MIN_CONCURRENCY + " and " + MAX_CONCURRENCY);
        }

        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put("group.id", "ConsumerSimpleType");
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaUrl);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        this.executor = Executors.newFixedThreadPool(concurrencyFactor);

        for (int i = 0; i < concurrencyFactor; i++) {
            this.executor.execute(new BuzzHandlerRunnable(props, _handlerMaps));
        }
    }

    public <T extends BuzzMessage> void add(String eventOrCommand, BuzzHandler<T> handler) {
        _handlerMaps.put(eventOrCommand, handler); // TODO currently only handle one

        bus.register(handler);
    }
}
