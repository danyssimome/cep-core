package com.pgu.cepcore.model.event;

import com.pgu.cepcore.model.metadata.ComplexEventMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexEvent {

    /**
     * Идентификатор комплексного события
     */
    private UUID id;

    /**
     * Время начала комплексного события(время первого атомарного события) в ISO 8601:2004 с указанием TZ
     */
    private Instant startTime;

    /**
     * Время конца комплексного события(время последнего атомарного события) в ISO 8601:2004 с указанием TZ
     */
    private Instant endTime;

    /**
     * Полезная нагрузка комплексного события
     */
    private ComplexEventMetadata complexEventMetadata;

    /**
     * Идентификаторов атомарного событий
     */
    private UUID atomicEventId;

    /**
     * Идентификаторов агрегированного событий
     */
    private UUID aggregatedEventId;
}
