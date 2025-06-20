package com.pgu.cepcore.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.kafka.schema")
public class KafkaSchemaValue {

    /**
     * URL регистрации и проверки отправляемого/получаемого сообщения
     */
    private String schemaRegistryUrl;

    /**
     * Отключает автоматическую регистрацию типа события,
     * чтобы оно не переопределяло объединение в качестве последней схемы в теме
     */
    private Boolean autoRegisterSchemas;

    /**
     * Заставляет сериализатор Avro искать последнюю версию схемы в теме (которая будет объединением)
     * и использовать ее для сериализации
     */
    private Boolean useLatestVersion;

    /**
     * Стратегия именования ключа subject
     */
    private String keySubjectNameStrategy;

    /**
     * Стратегия именования значения subject
     */
    private String valueSubjectNameStrategy;

    private KafkaAvroDeserializerConfig avroDeserializerConfig;

    @Data
    public static class KafkaAvroSerializerConfig {
    }

    @Data
    public static class KafkaAvroDeserializerConfig {

        /**
         * Опция подключения десериализаторов логических типов
         */
        private Boolean specificAvroReader;

    }
}
