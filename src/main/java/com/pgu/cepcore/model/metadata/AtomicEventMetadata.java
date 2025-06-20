package com.pgu.cepcore.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Эфемерную сущность, которая служит для обогащения атомарных события бизнес данными
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtomicEventMetadata {

    /**
     * Идентификатор метаданных атомарного события
     */
    private UUID id;

    /**
     * Данные обогащения атомарного события
     */
    private String information;
}
