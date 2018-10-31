package com.tambunan.bus;

import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class MessageListeners {
	
	@Value("${bootservice.kafka.servers}")
	private String servers;

	@Value("${bootservice.kafka.schemaregistry.url}")
	private String schemaUrl;

	private Gson gson = new Gson();

	private EventBus eventBus = new EventBus();

	// TODO .. we need to change this to Map of <string, List>
	private HashMap<String, BuzzHandler<?>> _handlerMaps = new HashMap<>();

	private Bus bus;

	@Autowired
	private ApplicationContext context;

	public void start() throws Exception {

		scanAllMessageHandler();

		Properties props = new Properties();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		props.put("group.id", "ConsumerSimpleType");
		props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaUrl);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

		if (_handlerMaps.keySet().size() == 0)
			throw new Exception("you need to subscribe to topics, please register message handler first");

		consumer.subscribe(_handlerMaps.keySet());

		while (true) {
			try {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5000));

				for (ConsumerRecord<String, String> record : records) {

					Header[] headers = record.headers().toArray();

					HashMap<String, String> kafkaHeader = new HashMap<>();

					Arrays.stream(headers).forEach(x -> {
                        kafkaHeader.put(x.key(), new String(x.value()));
                    });

					BuzzHeader header = new BuzzHeader(record.key(), record.offset(), record.partition(), record.timestamp(), record.topic(), kafkaHeader);

					BuzzMessage message = (BuzzMessage) gson.fromJson(record.value(), Class.forName(header.getMessageType()));

					BuzzEnvelop envelop = new BuzzEnvelop(message, new BuzzContextImpl(header, bus));

					System.out.println(header.getMessageType());

					eventBus.post(envelop);
				}

				consumer.commitSync();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public <T extends BuzzMessage> void add(String eventOrCommand, BuzzHandler<T> handler) {
		_handlerMaps.put(eventOrCommand, handler); // TODO currently only handle one

		eventBus.register(handler);
	}

	public void setBus(Bus eventBus) {
		this.bus = eventBus;
	}

	private void scanAllMessageHandler() {
		Map<String, Object> result = context.getBeansWithAnnotation(BuzzSubscribe.class);
		result.forEach((k, v) -> {
			BuzzHandler buzzHandler = (BuzzHandler) v;
			String topic = getTopic(v);
            System.out.println("handler found : " + buzzHandler.getClass() + " -> " + "topic : " + topic);

			add(topic, buzzHandler);
		});
	}

    private String getTopic(Object v) {
        Class<?> _class = v.getClass();
        if(_class.isAnnotationPresent(BuzzSubscribe.class)) {
            BuzzSubscribe ta = _class.getAnnotation(BuzzSubscribe.class);
            return ta.topic();
        }
        return "";
    }
}
