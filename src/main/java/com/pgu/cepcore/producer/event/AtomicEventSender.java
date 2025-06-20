package com.pgu.cepcore.producer.event;

import com.pgu.cepcore.model.event.raw.AtomicEventRawAvro;
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
public class AtomicEventSender {

    @Value("${spring.kafka.topics.atomic-raw}")
    private String atomicEventRawTopic;

    private final Producer<UUID, AtomicEventRawAvro> producerForAtomicEvent;

    public void sendAtomicEvent(AtomicEventRawAvro atomicEvent) {
        var atomicEventProducerRecord = new ProducerRecord<>(
                atomicEventRawTopic,
                UUID.fromString(atomicEvent.getId()),
                atomicEvent
        );

        producerForAtomicEvent.send(atomicEventProducerRecord, (metadata, exception) -> {
            if (exception != null) {
                log.error("Error while producing atomic event", exception);
                log.error("Dead letter message: {}", atomicEventProducerRecord);
            } else {
                log.info("Send message success!");
                log.info("Metadata:{" +
                        "Topic: " + metadata.topic() + " Partition: " + metadata.partition() +
                        " Offset: " + metadata.offset() + " Timestamp: " + metadata.timestamp() +
                        "}");
                log.info("Message: {}", atomicEvent);
            }
        });
    }
}
