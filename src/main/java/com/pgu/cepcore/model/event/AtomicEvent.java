package com.pgu.cepcore.model.event;

import com.pgu.cepcore.model.metadata.AtomicEventMetadata;
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
public class AtomicEvent {

    /**
     * Идентификатор атомарного события
     */
    private UUID id;

    /**
     * Гомогенный идентификатор совместности события
     */
    private Integer homogeneousCompatibilityId;

    /**
     * Гетерогенный идентификатор совместности события
     */
    private Integer heterogeneousCompatibilityId;

    /**
     * Время начала события в ISO 8601:2004 с указанием TZ
     */
    private Instant startTime;

    /**
     * Описание атомарного события
     */
    private String description;

    /**
     * Полезная нагрузка атомарного события
     */
    private AtomicEventMetadata atomicEventMetadata;
}
