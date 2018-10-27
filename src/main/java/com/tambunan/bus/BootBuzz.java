package com.tambunan.bus;

import com.google.gson.Gson;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class BootBuzz {
    private KafkaProducer<String, String> producer;
    private Gson gson = new Gson();

    public BootBuzz() {
        Properties props = new Properties();

        // TODO need to refactor this to properties file
        props.put("bootstrap.servers", "cloudera-01.com.tambunan.com:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://cloudera-01.com.tambunan.com:8081");

        producer = new KafkaProducer<String, String>(props);
    }

    public void send(String destination, BuzzMessage message) {

        Header header = new RecordHeader("message-type", message.getClass().getName().getBytes());

        List<Header> headers = new ArrayList<>();
        headers.add(header);

        producer.send(new ProducerRecord<String, String>(destination, 0, "", serialize(message), headers));

        System.out.println("send : " + message.getClass());
    }

    private String serialize(BuzzMessage message) {
        return gson.toJson(message);
    }

    public void publish(BuzzMessage message) {
        producer.send(new ProducerRecord<String, String>(message.getClass().getName(), serialize(message)));

        System.out.println("send : " + message.getClass());
    }
}
