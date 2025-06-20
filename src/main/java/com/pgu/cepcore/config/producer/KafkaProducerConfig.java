package com.pgu.cepcore.config.producer;


import com.pgu.cepcore.config.KafkaSchemaValue;
import com.pgu.cepcore.model.event.raw.AtomicEventRawAvro;
import com.pgu.cepcore.model.metadata.AggregatedEventMetadataAvro;
import com.pgu.cepcore.model.metadata.AtomicEventMetadataAvro;
import com.pgu.cepcore.model.metadata.ComplexEventMetadataAvro;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.UUID;

@Slf4j
@Configuration
@EnableConfigurationProperties({KafkaProducerSpecificValue.class, KafkaSchemaValue.class})
public class KafkaProducerConfig {

    private static final String ATOMIC_EVENT_PRODUCER = "atomic-event-producer";
    private static final String ATOMIC_EVENT_METADATA_PRODUCER = "atomic-event-metadata-producer";
    private static final String AGGREGATED_EVENT_METADATA_PRODUCER = "aggregated-event-metadata-producer";
    private static final String COMPLEX_EVENT_METADATA_PRODUCER = "complex-event-metadata-producer";

    @Bean(destroyMethod = "close")
    public Producer<UUID, AtomicEventRawAvro> atomicEventProducerConfig(final KafkaProperties kafkaProperties,
                                                                        final KafkaSchemaValue kafkaSchemaValue,
                                                                        final KafkaProducerSpecificValue kafkaProducerSpecificValue) {

        var properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, ATOMIC_EVENT_PRODUCER);
        //properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, AtomicEventPartitioner.class);
        properties.putAll(eventProducerConfig(kafkaProperties, kafkaSchemaValue, kafkaProducerSpecificValue));
        return new KafkaProducer<>(properties);
    }

    @Bean(destroyMethod = "close")
    public Producer<UUID, AtomicEventMetadataAvro> atomicEventMetadataProducerConfig(final KafkaProperties kafkaProperties,
                                                                                     final KafkaSchemaValue kafkaSchemaValue,
                                                                                     final KafkaProducerSpecificValue kafkaProducerSpecificValue) {

        var properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, ATOMIC_EVENT_METADATA_PRODUCER);
        //properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, .class);
        properties.putAll(eventProducerConfig(kafkaProperties, kafkaSchemaValue, kafkaProducerSpecificValue));
        return new KafkaProducer<>(properties);
    }

    @Bean(destroyMethod = "close")
    public Producer<UUID, AggregatedEventMetadataAvro> aggregatedEventMetadataProducerConfig(final KafkaProperties kafkaProperties,
                                                                                             final KafkaSchemaValue kafkaSchemaValue,
                                                                                             final KafkaProducerSpecificValue kafkaProducerSpecificValue) {

        var properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AGGREGATED_EVENT_METADATA_PRODUCER);
        //properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, .class);
        properties.putAll(eventProducerConfig(kafkaProperties, kafkaSchemaValue, kafkaProducerSpecificValue));
        return new KafkaProducer<>(properties);
    }

    @Bean(destroyMethod = "close")
    public Producer<UUID, ComplexEventMetadataAvro> complexEventMetadataProducerConfig(final KafkaProperties kafkaProperties,
                                                                                       final KafkaSchemaValue kafkaSchemaValue,
                                                                                       final KafkaProducerSpecificValue kafkaProducerSpecificValue) {

        var properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, COMPLEX_EVENT_METADATA_PRODUCER);
        //properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, .class);
        properties.putAll(eventProducerConfig(kafkaProperties, kafkaSchemaValue, kafkaProducerSpecificValue));
        return new KafkaProducer<>(properties);
    }

    private Properties eventProducerConfig(final KafkaProperties kafkaProperties,
                                           final KafkaSchemaValue kafkaSchemaValue,
                                           final KafkaProducerSpecificValue kafkaProducerSpecificValue) {
        // --Задачи настройки производителя--
        // Настройки сериализации данных
        // Настройка пакетной отправки
        // Настройка гарантированной доставки
        // Настройка идемпотентность/транзакционность
        // Настройка секционирования
        // Настройка асинхронной отправки
        // deadLetterQueue
        var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getKeySerializer());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getValueSerializer());
        properties.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaSchemaValue.getSchemaRegistryUrl());
        properties.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, kafkaSchemaValue.getAutoRegisterSchemas());
        properties.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, kafkaSchemaValue.getUseLatestVersion());
        //На данный момент ключ не сериализуется под avro
        //properties.put(AbstractKafkaSchemaSerDeConfig.KEY_SUBJECT_NAME_STRATEGY, kafkaSchemaRegistryConfig.getKeySubjectNameStrategy());
        properties.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, kafkaSchemaValue.getValueSubjectNameStrategy());
        //properties.put(KafkaAvroSerializerConfig.AVRO_USE_LOGICAL_TYPE_CONVERTERS_CONFIG, true);

        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerSpecificValue.getBatchSize());
        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, kafkaProducerSpecificValue.getMaxRequestSize());
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProducerSpecificValue.getBufferMemory());
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, kafkaProducerSpecificValue.getMaxBlockMs());
        properties.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerSpecificValue.getLingerMs());
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerSpecificValue.getRequestTimeoutMs());
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, kafkaProducerSpecificValue.getRetryBackoffMs());
        properties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, kafkaProducerSpecificValue.getDeliveryTimeoutMs());
        properties.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerSpecificValue.getRetries());
        properties.put(ProducerConfig.ACKS_CONFIG, kafkaProducerSpecificValue.getAcks());
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, kafkaProducerSpecificValue.getEnableIdempotence());
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, kafkaProducerSpecificValue.getMaxInFlightRequestsPerConnection());
        return properties;
    }
}
