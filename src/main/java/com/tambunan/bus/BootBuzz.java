package com.tambunan.bus;

import java.util.ArrayList;
import java.util.List;
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

	private static final Logger log = LoggerFactory.getLogger(BootBuzz.class);

	public BootBuzz() {

	}

	@PostConstruct
	public void postConstruct() {
		Properties props = new Properties();

		log.debug("Bootstrapping : " + servers);

		// TODO need to refactor this to properties file
		props.put("bootstrap.servers", servers);
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaUrl);

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

		publisher
				.send(new ProducerRecord<String, String>(event.getClass().getName(), 0, "", serialize(event), headers));

		System.out.println("send : " + event.getClass());
	}

	@Override
	public void start() {
		// TODO need to change to executor thread pool
		new Thread(() -> {
			try {
				log.debug("bus listener starting...");
				listeners.start();
				log.debug("bus listener started...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

	}
}
