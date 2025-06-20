package com.pgu.cepcore.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Эфемерную сущность, которая служит для обогащения агрегированных события бизнес данными
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedEventMetadata {

    /**
     * Идентификатор метаданных агрегированного события
     */
    private UUID id;

    /**
     * Данные обогащения агрегированного события
     */
    private String information;
}
