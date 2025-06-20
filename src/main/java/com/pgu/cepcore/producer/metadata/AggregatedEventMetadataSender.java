package com.pgu.cepcore.producer.metadata;

import com.pgu.cepcore.model.metadata.AggregatedEventMetadataAvro;
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
public class AggregatedEventMetadataSender {

    @Value("${spring.kafka.topics.aggregated-metadata}")
    private String aggregatedEventRawTopic;

    private final Producer<UUID, AggregatedEventMetadataAvro> producerForAggregatedEventMetadata;

    public void sendAggregatedEventMetadata(AggregatedEventMetadataAvro aggregatedEventMetadata) {
        var aggregatedEventMetadataProducerRecord = new ProducerRecord<>(
            aggregatedEventRawTopic,
            UUID.fromString(aggregatedEventMetadata.getId()),
            aggregatedEventMetadata
        );

        //TODO: транзакционность
        //TODO: deadLetterQueue
        producerForAggregatedEventMetadata.send(aggregatedEventMetadataProducerRecord, (metadata, exception) -> {
            if (exception != null) {
                log.error("Error while producing atomic event", exception);
                log.error("Dead letter message: {}", aggregatedEventMetadataProducerRecord);
            } else {
                log.info("Send message success!");
                log.info("Metadata:{" +
                    "Topic: " + metadata.topic() + " Partition: " + metadata.partition() +
                    " Offset: " + metadata.offset() + " Timestamp: " + metadata.timestamp() +
                    "}");
                log.info("Message: {}", aggregatedEventMetadata);
            }
        });
    }
}
