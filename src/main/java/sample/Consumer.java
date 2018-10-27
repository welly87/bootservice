package sample;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "cloudera-01.com.tambunan.com:9092");
        props.put("group.id", "ConsumerSimpleType");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://cloudera-01.com.tambunan.com:8081");

        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<Integer, String>(props);
        consumer.subscribe(Collections.singletonList("tambunanw"));
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Long.MAX_VALUE);
            process(records); // application-specific processing
            consumer.commitSync();
        }
    }

    private static void process(ConsumerRecords<Integer, String> records) {
        System.out.println("records");
    }
}
