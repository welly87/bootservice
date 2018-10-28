package com.tambunan.bus;

import com.google.gson.Gson;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class BootBuzz implements Bus {
    private KafkaProducer<String, String> publisher;

    private Gson gson = new Gson();

    private MessageListeners listeners = new MessageListeners();

    @Value("${kafka.servers}")
    private String servers;

    @Value("${kafka.schemaregistry.url}")
    private String schemaUrl;

    public BootBuzz() {
        Properties props = new Properties();

        // TODO need to refactor this to properties file
        props.put("bootstrap.servers", "cloudera-01.com.tambunan.com:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://cloudera-01.com.tambunan.com:8081");

        publisher = new KafkaProducer<String, String>(props);
    }

    @Override
    public <T extends BuzzMessage> void subscribe(String eventName, BuzzHandler<T> handler) {
        listeners.add(eventName, handler);
    }

    @Override
    public <T extends BuzzMessage> void handleCommand(String commandName, BuzzHandler<T> handler) {
        listeners.add(commandName, handler);
    }

    @Override
    public void send(String destination, BuzzMessage cmd) {

        Header header = new RecordHeader("message-type", cmd.getClass().getName().getBytes());

        List<Header> headers = new ArrayList<>();
        headers.add(header);

        publisher.send(new ProducerRecord<String, String>(destination, 0, "", serialize(cmd), headers));

        System.out.println("send : " + cmd.getClass());
    }

    private String serialize(BuzzMessage message) {
        return gson.toJson(message);
    }

    @Override
    public void publish(BuzzMessage event) {

        Header header = new RecordHeader("message-type", event.getClass().getName().getBytes());

        List<Header> headers = new ArrayList<>();
        headers.add(header);

        publisher.send(new ProducerRecord<String, String>(event.getClass().getName(), 0, "", serialize(event), headers));

        System.out.println("send : " + event.getClass());
    }

    @Override
    public void start() {
        try {
            listeners.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
