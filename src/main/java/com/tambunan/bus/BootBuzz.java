package com.tambunan.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;

@Service
public class BootBuzz implements Bus {
    private KafkaProducer<String, String> publisher;

    private Gson gson = new Gson();

    @Autowired
    private MessageListeners listeners; // = new MessageListeners();

    @Value("${bootservice.kafka.servers}")
    private String servers;

    @Value("${bootservice.kafka.schemaregistry.url}")
    private String schemaUrl;

    @Value("${bootservice.kafka.producer.acks-config:all}")
    private String acksConfig;

    @Value("${bootservice.kafka.producer.retries-config:0}")
    private int retriesConfig;

    private static final Logger log = LoggerFactory.getLogger(BootBuzz.class);

    @Autowired
    private ApplicationContext context;

    public BootBuzz() {

    }

    @PostConstruct
    public void postConstruct() {
        Properties props = new Properties();

        props.put("bootstrap.servers", servers);
        props.put(ProducerConfig.ACKS_CONFIG, acksConfig);
        props.put(ProducerConfig.RETRIES_CONFIG, retriesConfig);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaUrl);

        publisher = new KafkaProducer<String, String>(props);

        Map<String, Object> result = context.getBeansWithAnnotation(BuzzSubscribe.class);
        result.forEach((k, v) -> {
            BuzzHandler buzzHandler = (BuzzHandler) v;
            // FIXME should get from annotation value : "topic"
            subscribe("com.tambunan.messages.CalculatePayroll", buzzHandler);
        });
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

        publisher
                .send(new ProducerRecord<String, String>(event.getClass().getName(), 0, "", serialize(event), headers));

        System.out.println("send : " + event.getClass());
    }

    @Override
    public void start() {
        // Thread pool on listeners.start();
        try {
            log.debug("bus listener starting...");
            listeners.start();
            log.debug("bus listener started...");
        } catch (Exception e) {
            log.error("bus listener exception : " + e.getMessage());
            e.printStackTrace();
        }

    }
}
