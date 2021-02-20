package com.example.demo.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value("${topic.message}")
    private String messageName;

    @Value("${topic.greeting}")
    private String greetingName;

    @Value("${topic.filtered}")
    private String filteredName;

    @Value("${topic.partitioned}")
    private String partitionedName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map configs = new HashMap<String, Object>() {{
            put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        }};
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(messageName, 1, (short)1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(partitionedName, 6, (short)1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic(filteredName, 1, (short)1);
    }

    @Bean
    public NewTopic topic4() {
        return new NewTopic(greetingName, 1, (short)1);
    }
}
