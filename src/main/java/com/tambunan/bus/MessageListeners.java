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

import java.lang.reflect.Type;
import java.util.*;

public class MessageListeners {
    private final KafkaConsumer<String, String> _consumer;


    @Autowired
    private List<BuzzHandler<? extends BuzzMessage>> _handlers;

    private HashMap<Class, BuzzHandler<? extends BuzzMessage>> _handlerMaps = new HashMap<>();

    public MessageListeners() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "cloudera-01.com.tambunan.com:9092");
        props.put("group.id", "ConsumerSimpleType");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://cloudera-01.com.tambunan.com:8081");

        _consumer = new KafkaConsumer<String, String>(props);

        _consumer.subscribe(Collections.singletonList("welly"));
    }

    public void start() throws Exception {

        while (true) {
            ConsumerRecords<String, String> records = _consumer.poll(Long.MAX_VALUE);

            for (ConsumerRecord<String, String> record : records) {

                String messageType = new String(Arrays.stream(record.headers().toArray()).filter(x -> x.key().equals("message-type")).findFirst().get().value());

//                for (Header header : record.headers()) {
//                    System.out.println(header.key() + " : " + new String(header.value()));
//                }

                Gson gson = new Gson();
                Type listType = new TypeToken<Employee>(){}.getType();

                BuzzMessage message = gson.fromJson(record.value(), listType);
            }
        }
    }
}
