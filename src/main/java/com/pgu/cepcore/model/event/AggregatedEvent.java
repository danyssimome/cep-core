package com.pgu.cepcore.model.event;

import com.pgu.cepcore.model.metadata.AggregatedEventMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedEvent {

    /**
     * Идентификатор агрегированного события
     */
    private UUID id;

    /**
     * Гетерогенный идентификатор совместности события
     */
    private Integer heterogeneousCompatibilityId;

    /**
     * Время начала агрегированного события(время первого атомарного события) в ISO 8601:2004 с указанием TZ
     */
    private Instant startTime;

    /**
     * Время конца агрегированного события(время последнего элементарного события) в ISO 8601:2004 с указанием TZ
     */
    private Instant endTime;

    /**
     * Полезная нагрузка агрегированного события
     */
    private AggregatedEventMetadata aggregatedEventMetadata;

    /**
     * Коллекция идентификаторов агрегированных атомарных событий
     */
    private List<UUID> atomicEventIds;
}
