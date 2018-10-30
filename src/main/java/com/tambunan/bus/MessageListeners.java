package com.tambunan.bus;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import com.google.common.eventbus.EventBus;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;

@Component
public class MessageListeners {
	
	@Value("${bootservice.kafka.servers}")
	private String servers;

	@Value("${bootservice.kafka.schemaregistry.url}")
	private String schemaUrl;

	private Gson gson = new Gson();

	private EventBus bus = new EventBus();

	// TODO .. we need to change this to Map of <string, List>
	private HashMap<String, BuzzHandler<?>> _handlerMaps = new HashMap<>();

	public void start() throws Exception {
		Properties props = new Properties();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		props.put("group.id", "ConsumerSimpleType");
		props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaUrl);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		KafkaConsumer<String, String> _consumer = new KafkaConsumer<String, String>(props);

		if (_handlerMaps.keySet().size() == 0)
			throw new Exception("you need to subscribe to topics, please register message handler first");

		_consumer.subscribe(_handlerMaps.keySet());

		while (true) {
			try {
				ConsumerRecords<String, String> records = _consumer.poll(Duration.ofSeconds(5000));

				for (ConsumerRecord<String, String> record : records) {

					String messageType = new String(Arrays.stream(record.headers().toArray())
							.filter(x -> x.key().equals("message-type")).findFirst().get().value());

					BuzzMessage message = (BuzzMessage) gson.fromJson(record.value(), Class.forName(messageType));

					System.out.println(messageType);

					bus.post(message);
				}

				_consumer.commitSync();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public <T extends BuzzMessage> void add(String eventOrCommand, BuzzHandler<T> handler) {
		_handlerMaps.put(eventOrCommand, handler); // TODO currently only handle one

		bus.register(handler);
	}
}
