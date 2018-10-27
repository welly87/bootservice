import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        var props = new Properties();
        props.put("bootstrap.servers", "cloudera-01.tambunan.com:9092");
        props.put("group.id", "ConsumerSimpleType");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        var consumer = new KafkaConsumer<Integer, String>(props);
        consumer.subscribe(Collections.singletonList("tambunanw"));
        while (true) {
            var records = consumer.poll(Long.MAX_VALUE);
            process(records); // application-specific processing
            consumer.commitSync();
        }
    }

    private static void process(ConsumerRecords<Integer, String> records) {
        System.out.println("records");
    }
}
