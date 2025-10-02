package com.jpmc.midascore.config;

import com.jpmc.midascore.foundation.Transaction;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;

@TestConfiguration
public class TestConfig {
    
    @Bean
    @Primary
    @Profile("!embedded-kafka")
    @SuppressWarnings("unchecked")
    public KafkaTemplate<String, Transaction> kafkaTemplate() {
        return Mockito.mock(KafkaTemplate.class);
    }
}