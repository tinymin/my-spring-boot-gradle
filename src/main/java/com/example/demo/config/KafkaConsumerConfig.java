package com.example.demo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        Map props = new HashMap<String, Object>() {{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        }};
        return new DefaultKafkaConsumerFactory<>(props);
    }

    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(groupId));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> fooKafkaListenerContainerFactory() {
        return kafkaListenerContainerFactory("foo");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> barKafkaListenerContainerFactory() {
        return kafkaListenerContainerFactory("bar");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> headerKafkaListenerContainerFactory() {
        return kafkaListenerContainerFactory("header");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> partitionKafkaListenerContainerFactory() {
        return kafkaListenerContainerFactory("partition");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> filterKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = kafkaListenerContainerFactory("filter");
        factory.setRecordFilterStrategy(r -> r.value().contains("world"));
        return factory;
    }

    public ConsumerFactory<String, Greeting> greetingConsumerFactory() {
        Map props = new HashMap<String, Object>() {{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            put(ConsumerConfig.GROUP_ID_CONFIG, "greeting");
        }};

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Greeting.class));
    }
}
