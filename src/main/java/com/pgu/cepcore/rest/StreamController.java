package com.pgu.cepcore.rest;


import com.pgu.cepcore.model.event.raw.AtomicEventRawAvro;
import com.pgu.cepcore.model.metadata.AggregatedEventMetadataAvro;
import com.pgu.cepcore.model.metadata.AtomicEventMetadataAvro;
import com.pgu.cepcore.model.metadata.ComplexEventMetadataAvro;
import com.pgu.cepcore.producer.event.AtomicEventSender;
import com.pgu.cepcore.producer.metadata.AggregatedEventMetadataSender;
import com.pgu.cepcore.producer.metadata.AtomicEventMetadataSender;
import com.pgu.cepcore.producer.metadata.ComplexEventMetadataSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class StreamController {

    //private final KStream<UUID, AggregatedEventAvro> kStream2;
    //private final KStream<UUID, ComplexEventAvro> kStream3;
    //private final StreamsBuilder streamsBuilder;

    private final AtomicEventSender atomicEventSender;

    private final AtomicEventMetadataSender atomicEventMetadataSender;

    private final AggregatedEventMetadataSender aggregatedEventMetadataSender;

    private final ComplexEventMetadataSender complexEventMetadataSender;

    @PostMapping("/send-atomic")
    @ResponseStatus(HttpStatus.OK)
    public void send(@RequestParam @Positive Integer messageCount) {

        log.info("Starting to send {} messages...", messageCount);
        for (int i = 1; i < messageCount + 1; i++) {
            atomicEventSender.sendAtomicEvent(AtomicEventRawAvro.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setHomogeneousCompatibilityId(i)
                .setHeterogeneousCompatibilityId(i)//?
                .setStartTime(Instant.now())
                .setAtomicEventMetadataId("f18b309a-7fc0-479b-8467-5d655722d020")
                .build());
            atomicEventSender.sendAtomicEvent(AtomicEventRawAvro.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setHomogeneousCompatibilityId(i)
                .setHeterogeneousCompatibilityId(i)//?
                .setStartTime(Instant.now())
                .setAtomicEventMetadataId("48e41156-c328-42fa-81b2-98c0cef6822f")
                .build());
        }
        log.info("Ending to send {} messages...", messageCount);
    }

    @PostMapping("/send-atomic-metadata")
    @ResponseStatus(HttpStatus.OK)
    public void sendAM() {
        atomicEventMetadataSender.sendAtomicMetadataEvent(AtomicEventMetadataAvro.newBuilder()
            .setId("f18b309a-7fc0-479b-8467-5d655722d020")
            .setInformation("atomic-metadata-type-1")
            .build());
        atomicEventMetadataSender.sendAtomicMetadataEvent(AtomicEventMetadataAvro.newBuilder()
            .setId("48e41156-c328-42fa-81b2-98c0cef6822f")
            .setInformation("atomic-metadata-type-2")
            .build());
    }

    @PostMapping("/send-aggregated-metadata")
    @ResponseStatus(HttpStatus.OK)
    public void sendAgM() {
        aggregatedEventMetadataSender.sendAggregatedEventMetadata(AggregatedEventMetadataAvro.newBuilder()
            .setId("b4390368-1448-11ee-be56-0242ac120002")
            .setInformation("aggregated-metadata-type-1")
            .build());
    }

    @PostMapping("/send-complex-metadata")
    @ResponseStatus(HttpStatus.OK)
    public void send() {
        complexEventMetadataSender.sendComplexEventMetadata(ComplexEventMetadataAvro.newBuilder()
            .setId("8cfeab54-1448-11ee-be56-0242ac120002")
            .setInformation("complex-metadata-type-1")
            .build());

    }

/*    @GetMapping("/topoly")
    @ResponseStatus(HttpStatus.OK)
    public void getTopology(){
        var top = streamsBuilder.build();
        log.info(top.describe().toString());
    }*/
}
