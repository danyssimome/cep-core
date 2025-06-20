package com.pgu.cepcore.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class KafkaConfig {

    @Bean
    @Primary
    public KafkaProperties getKafkaProperties() {
        return new KafkaProperties();
    }
}
