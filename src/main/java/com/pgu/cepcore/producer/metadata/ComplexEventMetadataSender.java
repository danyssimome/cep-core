package com.pgu.cepcore.producer.metadata;

import com.pgu.cepcore.model.metadata.ComplexEventMetadataAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ComplexEventMetadataSender {

    @Value("${spring.kafka.topics.complex-metadata}")
    private String complexEventMetadataTopic;

    private final Producer<UUID, ComplexEventMetadataAvro> producerForComplexEventMetadata;

    public void sendComplexEventMetadata(ComplexEventMetadataAvro complexEventMetadata) {
        var complexEventMetadataProducerRecord = new ProducerRecord<>(
            complexEventMetadataTopic,
            UUID.fromString(complexEventMetadata.getId()),
            complexEventMetadata
        );

        //TODO: ошибки с логированием
        //TODO: транзакционность
        //TODO: deadLetterQueue
        producerForComplexEventMetadata.send(complexEventMetadataProducerRecord, (metadata, exception) -> {
            if (exception != null) {
                log.error("Error while producing atomic event", exception);
                log.error("Dead letter message: {}", complexEventMetadataProducerRecord);
            } else {
                log.info("Send message success!");
                log.info("Metadata:{" +
                    "Topic: " + metadata.topic() + " Partition: " + metadata.partition() +
                    " Offset: " + metadata.offset() + " Timestamp: " + metadata.timestamp() +
                    "}");
                log.info("Message: {}", complexEventMetadata);
            }
        });
        //TODO: производитель для metainfo
    }
}
