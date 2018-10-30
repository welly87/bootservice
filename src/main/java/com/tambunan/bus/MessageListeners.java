package com.tambunan.bus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

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

	// TODO .. we need to change this to Map of <string, List>
	private HashMap<String, BuzzHandler<BuzzMessage>> _handlerMaps = new HashMap<>();

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
				// TODO find the asynchronous way of getting the message, remove blocking code !
				ConsumerRecords<String, String> records = _consumer.poll(Long.MAX_VALUE);

				for (ConsumerRecord<String, String> record : records) {

					String messageType = new String(Arrays.stream(record.headers().toArray())
							.filter(x -> x.key().equals("message-type")).findFirst().get().value());

					BuzzHandler<BuzzMessage> handler = _handlerMaps.get(messageType);

					if (handler == null) continue;

					BuzzMessage message = (BuzzMessage) gson.fromJson(record.value(), Class.forName(messageType));

					System.out.println(messageType);

					handler.handle(message);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void add(String eventorcommand, BuzzHandler<BuzzMessage> handler) {
		_handlerMaps.put(eventorcommand, handler); // TODO currently only handle one
	}
}
