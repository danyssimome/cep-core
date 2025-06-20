package com.pgu.cepcore.producer.metadata;

import com.pgu.cepcore.model.metadata.AtomicEventMetadataAvro;
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
public class AtomicEventMetadataSender {

    @Value("${spring.kafka.topics.atomic-metadata}")
    private String atomicEventMetadataTopic;

    private final Producer<UUID, AtomicEventMetadataAvro> producerForAtomicEventMetadata;

    public void sendAtomicMetadataEvent(AtomicEventMetadataAvro atomicEventMetadata) {
        var atomicEventMetadataProducerRecord = new ProducerRecord<>(
            atomicEventMetadataTopic,
            UUID.fromString(atomicEventMetadata.getId()),
            atomicEventMetadata
        );

        //TODO: транзакционность
        //TODO: deadLetterQueue
        producerForAtomicEventMetadata.send(atomicEventMetadataProducerRecord, (metadata, exception) -> {
            if (exception != null) {
                log.error("Error while producing atomic event", exception);
                log.error("Dead letter message: {}", atomicEventMetadataProducerRecord);
            } else {
                log.info("Send message success!");
                log.info("Metadata:{" +
                    "Topic: " + metadata.topic() + " Partition: " + metadata.partition() +
                    " Offset: " + metadata.offset() + " Timestamp: " + metadata.timestamp() +
                    "}");
                log.info("Message: {}", atomicEventMetadata);
            }
        });
    }
}
