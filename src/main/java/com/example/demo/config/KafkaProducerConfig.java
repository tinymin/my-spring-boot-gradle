package com.example.demo.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerConfig {
    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, String> greetingKafkaTemplate() {
        return new KafkaTemplate<>(greetingProducerFactory());
    }

    public ProducerFactory<String, String> producerFactory() {
        Map configProps = new HashMap<String, Object>() {{
           put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
           put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
           put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        }};

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    public ProducerFactory<String, String> greetingProducerFactory() {
        Map configProps = new HashMap<String, Object>() {{
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        }};

        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
