package com.tambunan.bus;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import com.tambunan.domain.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.*;

@Component
public class MessageListeners {
    @Value("${kafka.servers}")
    private String servers;

    Gson gson = new Gson();

    // TODO .. we need to change this to Map of <string, List>
    private HashMap<String, BuzzHandler<? extends BuzzMessage>> _handlerMaps = new HashMap<>();

    public void start() throws Exception {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "cloudera-01.tambunan.com:9092");
        props.put("group.id", "ConsumerSimpleType");
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://cloudera-01.tambunan.com:8081");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        KafkaConsumer<String, String> _consumer = new KafkaConsumer<String, String>(props);

        _consumer.subscribe(_handlerMaps.keySet());

        while (true) {
            try {
                // TODO find the asynchronous way of getting the message, remove blocking code !
                ConsumerRecords<String, String> records = _consumer.poll(Long.MAX_VALUE);

                for (ConsumerRecord<String, String> record : records) {

                    String messageType = new String(Arrays.stream(record.headers().toArray()).filter(x -> x.key().equals("message-type")).findFirst().get().value());

                    BuzzHandler<? extends BuzzMessage> handler = _handlerMaps.get(messageType);

                    BuzzMessage message = (BuzzMessage) gson.fromJson(record.value(), Class.forName(messageType));

                    System.out.println(messageType);

                    // TODO how to handle this message
                }

            } catch (Exception e) {

            }

        }
    }

    public <T extends BuzzMessage> void add(String eventorcommand, BuzzHandler<T> handler) {
        _handlerMaps.put(eventorcommand, handler); // TODO currently only handle one
    }
}
